package xyz.dsemikin.wellfedcat.datastore.db.h2;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("FieldCanBeLocal")
public class DishDao {

    private final String ALL_DISHES_QUERY = """
            select
                dish.public_id                as dish_public_id,
                dish.name                     as dish_name,
                dish_meal_time.meal_time_name as meal_time
            from dish
            left join dish_meal_time on (dish_meal_time.dish_public_id = dish.public_id)
            """;

    private final String SELECT_DISH_BY_ID_QUERY = """
            select
                dish.public_id                as dish_public_id,
                dish.name                     as dish_name,
                dish_meal_time.meal_time_name as meal_time
            from dish
            left join dish_meal_time on (dish_meal_time.dish_public_id = dish.public_id)
            where dish.public_id = ?
            """;

    private final String SELECT_DISH_BY_NAME_QUERY = """
            select
                dish.public_id                as dish_public_id,
                dish.name                     as dish_name,
                dish_meal_time.meal_time_name as meal_time
            from dish
            left join dish_meal_time on (dish_meal_time.dish_public_id = dish.public_id)
            where dish.name = ?
            """;

    private final String INSERT_DISH_QUERY = """
            insert into dish (public_id, name)
            values (?, ?)
            """;

    private final String INSERT_DISH_MEAL_TIME_QUERY = """
            insert into dish_meal_time (dish_public_id, meal_time_name)
            values (?, ?)
            """;

    private final String DELETE_DISH_BY_NAME_QUERY = """
            delete from dish
            where name = ?
            """;

    private final String DELETE_DISH_BY_ID_QUERY = """
            delete from dish
            where public_id = ?
            """;

    private final Connection connection; // don't use directly. Use connection() instead, so that it later can be replaced with the pool

    public DishDao(final Connection connection) {
        this.connection = connection;
    }

    public List<Dish> allDishes() throws SQLException {

        try(PreparedStatement allDishesStatement = connection().prepareStatement(ALL_DISHES_QUERY)) {

            ResultSet resultSet = allDishesStatement.executeQuery();

            final List<Dish> dishes = new ArrayList<>();
            String previousDishPublicId = "";
            String previousDishName = "";
            Set<MealTime> mealTimes = new HashSet<>();

            while (resultSet.next()) {
                final String dishPublicId = resultSet.getString("dish_public_id");
                final String dishName = resultSet.getString("dish_name");
                if (!previousDishName.equals(dishName)) {

                    if (!previousDishPublicId.isEmpty()) {
                        final Dish dish = new Dish(previousDishPublicId, previousDishName, mealTimes);
                        dishes.add(dish);
                    }

                    previousDishPublicId = dishPublicId;
                    previousDishName = dishName;
                    mealTimes = new HashSet<>();
                }
                final String mealTimeString = resultSet.getString("meal_time");
                if (mealTimeString != null && !mealTimeString.isEmpty()) {
                    final MealTime mealTime = MealTime.valueOf(mealTimeString);
                    mealTimes.add(mealTime);
                }
            }

            final Dish dish = new Dish(previousDishPublicId, previousDishName, mealTimes);
            dishes.add(dish);

            return dishes;
        }
    }

    public Optional<Dish> dishByName(final String dishName) throws SQLException {

        try(PreparedStatement getDishStatement = connection().prepareStatement(SELECT_DISH_BY_NAME_QUERY)) {

            getDishStatement.setString(1, dishName);
            ResultSet resultSet = getDishStatement.executeQuery();
            String dishPublicId = "";
            final Set<MealTime> mealTimes = new HashSet<>();
            int rowCount = 0;
            while(resultSet.next()) {
                rowCount = resultSet.getRow();
                dishPublicId = resultSet.getString("dish_public_id");
                final String currentDishName = resultSet.getString("dish_name");
                if (!dishName.equals(currentDishName)) {
                    throw new IllegalStateException("More then one result received, while getting dish: " + dishName);
                }
                final String mealTimeString = resultSet.getString("meal_time");
                if (mealTimeString != null && !mealTimeString.isEmpty()) {
                    final MealTime mealTime = MealTime.valueOf(mealTimeString);
                    mealTimes.add(mealTime);
                }
            }

            if (rowCount == 0) {
                return Optional.empty();
            }

            return Optional.of(new Dish(dishPublicId, dishName, mealTimes));
        }

    }

    public Optional<Dish> dishById(final String dishPublicId) throws SQLException {

        try(PreparedStatement getDishStatement = connection().prepareStatement(SELECT_DISH_BY_ID_QUERY)) {

            getDishStatement.setString(1, dishPublicId);
            ResultSet resultSet = getDishStatement.executeQuery();
            final Set<MealTime> mealTimes = new HashSet<>();
            String dishName = "";
            int rowCount = 0;
            while(resultSet.next()) {
                rowCount = resultSet.getRow();
                final String currentDishPublicId = resultSet.getString("dish_public_id");
                dishName = resultSet.getString("dish_name");
                if (!dishPublicId.equals(currentDishPublicId)) {
                    throw new IllegalStateException("More then one result received, while getting dish: " + dishPublicId);
                }
                final String mealTimeString = resultSet.getString("meal_time");
                if (mealTimeString != null && !mealTimeString.isEmpty()) {
                    final MealTime mealTime = MealTime.valueOf(mealTimeString);
                    mealTimes.add(mealTime);
                }
            }

            if (rowCount == 0) {
                return Optional.empty();
            }

            return Optional.of(new Dish(dishPublicId, dishName, mealTimes));
        }
    }

    public boolean addDish(Dish dish) throws SQLException {
        // This solution is not perfect. One can insert dish between we check and insert the dish.
        // But for now it is considered to be least evil. Alternatives would be:
        // - try to insert and catch exception. Exception is not JDBC standard, so it would make code
        //   significantly DB-dependent.
        // - check if there is some JDBC api to lock table. Maybe not, then we have same problem,
        //   as for point 1. But even if there is, then it still makes things too complicated.
        if (this.dishByName(dish.name()).isPresent() || this.dishById(dish.publicId()).isPresent()) {
            return false;
        }
        final boolean initialAutoCommit = connection().getAutoCommit();
        try {
            connection().setAutoCommit(false);
            try (PreparedStatement insertDishStatement = connection().prepareStatement(INSERT_DISH_QUERY)) {
                insertDishStatement.setString(1, dish.publicId());
                insertDishStatement.setString(2, dish.name());
                final int insertedRowsCount = insertDishStatement.executeUpdate();
                if (insertedRowsCount != 1) {
                    connection().rollback();
                    return false;
                }
            }
            try (PreparedStatement insertDishMealTimeStatement = connection().prepareStatement(INSERT_DISH_MEAL_TIME_QUERY)) {
                for (MealTime mealTime : dish.suitableForMealTimes()) {
                    insertDishMealTimeStatement.setString(1, dish.publicId());
                    insertDishMealTimeStatement.setString(2, mealTime.toString());
                    final int insertedRowsCount = insertDishMealTimeStatement.executeUpdate();
                    if (insertedRowsCount != 1) {
                        connection().rollback();
                        return false;
                    }
                }
            }
            connection().commit();
            return true;
        } catch (Exception e) {
            connection().rollback();
            throw e;
        } finally {
            connection().setAutoCommit(initialAutoCommit);
        }
    }

    public DishStoreEditable.RemoveStatus removeDishByName(String name) throws SQLException {
        try (PreparedStatement deleteDishStatement = connection().prepareStatement(DELETE_DISH_BY_NAME_QUERY)) {
            deleteDishStatement.setString(1, name);
            // we don't need to delete dish_meal_time because of "cascade" delete policy.
            final int deletedRowsCount = deleteDishStatement.executeUpdate();
            return deletedRowsCount == 1 ? DishStoreEditable.RemoveStatus.SUCCESS : DishStoreEditable.RemoveStatus.DOES_NOT_EXIST;
        }
    }

    public DishStoreEditable.RemoveStatus removeDishById(String dishPublicId) throws SQLException {
        try (PreparedStatement deleteDishStatement = connection().prepareStatement(DELETE_DISH_BY_ID_QUERY)) {
            deleteDishStatement.setString(1, dishPublicId);
            // we don't need to delete dish_meal_time because of "cascade" delete policy.
            final int deletedRowsCount = deleteDishStatement.executeUpdate();
            return deletedRowsCount == 1 ? DishStoreEditable.RemoveStatus.SUCCESS : DishStoreEditable.RemoveStatus.DOES_NOT_EXIST;
        }
    }

    private Connection connection() {
        return connection;
    }

}
