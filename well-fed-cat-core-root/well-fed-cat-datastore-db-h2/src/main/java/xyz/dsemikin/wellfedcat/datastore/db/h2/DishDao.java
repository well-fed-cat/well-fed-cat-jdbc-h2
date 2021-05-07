package xyz.dsemikin.wellfedcat.datastore.db.h2;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
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
                dish.name                     as dish_name,
                dish_meal_time.meal_time_name as meal_time
            from dish
            left join dish_meal_time on (dish_meal_time.dish_name = dish.name)
            """;

    private final String SELECT_DISH_QUERY = """
            select
                dish.name as dish_name,
                dish_meal_time.meal_time_name as meal_time
            from dish
            left join dish_meal_time on (dish_meal_time.dish_name = dish.name)
            where dish.name = ?
            """;

    private final String INSERT_DISH_QUERY = """
            insert into dish (name)
            values (?)
            """;

    private final String INSERT_DISH_MEAL_TIME_QUERY = """
            insert into dish_meal_time (dish_name, meal_time_name)
            values (?, ?)
            """;

    private final String DELETE_DISH_QUERY = """
            delete from dish
            where name = ?
            """;

    private final Connection connection; // don't use directly. Use connection() instead, so that it later can be replaced with the pool

    public DishDao(final Connection connection) {
        this.connection = connection;
    }

    public List<Dish> allDishes() {

        try(PreparedStatement allDishesStatement = connection().prepareStatement(ALL_DISHES_QUERY)) {

            ResultSet resultSet = allDishesStatement.executeQuery();

            final List<Dish> dishes = new ArrayList<>();
            String previousDishName = "";
            Set<MealTime> mealTimes = new HashSet<>();

            while (resultSet.next()) {
                final String dishName = resultSet.getString("dish_name");
                if (!previousDishName.equals(dishName)) {

                    final Dish dish = new Dish(previousDishName, mealTimes);
                    dishes.add(dish);

                    previousDishName = dishName;
                    mealTimes = new HashSet<>();
                }
                final String mealTimeString = resultSet.getString("meal_time");
                if (mealTimeString != null && !mealTimeString.isEmpty()) {
                    final MealTime mealTime = MealTime.valueOf(mealTimeString);
                    mealTimes.add(mealTime);
                }
            }

            final Dish dish = new Dish(previousDishName, mealTimes);
            dishes.add(dish);

            return dishes;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query all dishes.", e);
        }
    }

    public Optional<Dish> dish(String dishName) {

        try(PreparedStatement getDishStatement = connection().prepareStatement(SELECT_DISH_QUERY)) {

            getDishStatement.setString(1, dishName);
            ResultSet resultSet = getDishStatement.executeQuery();
            final Set<MealTime> mealTimes = new HashSet<>();
            while(resultSet.next()) {
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

            if (resultSet.getRow() == 0) {
                return Optional.empty();
            }

            return Optional.of(new Dish(dishName, mealTimes));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query dish.", e);
        }
    }

    public void addDish(Dish dish) {
        final boolean initialAutoCommit;
        try {
            initialAutoCommit = connection().getAutoCommit();
            try {
                connection().setAutoCommit(false);
                try (PreparedStatement insertDishStatement = connection().prepareStatement(INSERT_DISH_QUERY)) {
                    insertDishStatement.setString(1, dish.name());
                    final int insertedRowsCount = insertDishStatement.executeUpdate();
                    if (insertedRowsCount != 1) {
                        throw new IllegalStateException("Failed to insert dish: " + dish.name());
                    }
                }
                try (PreparedStatement insertDishMealTimeStatement = connection().prepareStatement(INSERT_DISH_MEAL_TIME_QUERY)) {
                    for (MealTime mealTime : dish.suitableForMealTimes()) {
                        insertDishMealTimeStatement.setString(1, dish.name());
                        insertDishMealTimeStatement.setString(2, mealTime.toString());
                        final int insertedRowsCount = insertDishMealTimeStatement.executeUpdate();
                        if (insertedRowsCount != 1) {
                            throw new IllegalStateException("Failed to inset meal time " + mealTime + " for dish " + dish.name());
                        }
                    }
                }
                connection().commit();
            } catch (Exception e) {
                connection().rollback();
                throw e;
            } finally {
                connection().setAutoCommit(initialAutoCommit);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert dish.", e);
        }
    }

    public boolean removeDish(String name) {
        try (PreparedStatement deleteDishStatement = connection().prepareStatement(DELETE_DISH_QUERY)) {
            deleteDishStatement.setString(1, name);
            // we don't need to delete dish_meal_time because of "cascade" delete policy.
            final int deletedRowsCount = deleteDishStatement.executeUpdate();
            return deletedRowsCount == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove dish.", e);
        }
    }

    private Connection connection() {
        return connection;
    }

}
