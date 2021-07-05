package xyz.dsemikin.wellfedcat.datastore.db.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.StoreException;
import xyz.dsemikin.wellfedcat.datamodel.implementation.DayMenuSkeleton;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings({"ClassCanBeRecord", "unused"})
public class MenuTimelineDao {

    // TODO: convert SQLException into StoreException for methods of this class.

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuTimelineDao.class);

    private final Connection connection; // don't use directly. Use connection() instead, so that it later can be replaced with the pool

    public MenuTimelineDao(final Connection connection) {
        this.connection = connection;
    }

    private static final String SELECT_DISHES_FOR_DATE_QUERY = """
            select
                meal_time,
                dish_public_id,
                dish_position
            from menu_timeline
            where menu_date = ?
            """;

    public DayMenuSkeleton menuFor(final LocalDate date) {

        record DishWithPosition (String dishId, int dishPosition){}

        Map<MealTime, List<DishWithPosition>> dishes = new HashMap<>();
        dishes.put(MealTime.BREAKFAST, new ArrayList<>());
        dishes.put(MealTime.LUNCH, new ArrayList<>());
        dishes.put(MealTime.SUPPER, new ArrayList<>());

        try (PreparedStatement statement = connection().prepareStatement(SELECT_DISHES_FOR_DATE_QUERY)) {
            statement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final MealTime mealTime = MealTime.valueOf(resultSet.getString("meal_time"));
                final String dishId = resultSet.getString("dish_public_id");
                final int dishPosition = resultSet.getInt("dish_position");
                dishes.get(mealTime).add(new DishWithPosition(dishId, dishPosition));
            }
        } catch (SQLException e) {
            throw new StoreException("Error happened while selecting dishes for day.", e);
        }

        final var comparator = new Comparator<DishWithPosition>() {
            @Override
            public int compare(DishWithPosition o1, DishWithPosition o2) {
                return o1.dishPosition() - o2.dishPosition();
            }
        };

        final List<String> sortedBreakfast = dishes.get(MealTime.BREAKFAST).stream()
                .sorted(comparator)
                .map(DishWithPosition::dishId)
                .collect(Collectors.toList());
        final List<String> sortedLunch = dishes.get(MealTime.LUNCH).stream()
                .sorted(comparator)
                .map(DishWithPosition::dishId)
                .collect(Collectors.toList());
        final List<String> sortedSupper = dishes.get(MealTime.SUPPER).stream()
                .sorted(comparator)
                .map(DishWithPosition::dishId)
                .collect(Collectors.toList());

        final LinkedHashSet<String> breakfast = new LinkedHashSet<>(sortedBreakfast);
        final LinkedHashSet<String> lunch = new LinkedHashSet<>(sortedLunch);
        final LinkedHashSet<String> supper = new LinkedHashSet<>(sortedSupper);

        return new DayMenuSkeleton(breakfast, lunch, supper, date);
    }


    private static final String SELECT_IS_MENU_TIMELINE_EMPTY = """
            select count(*) = 0 as is_empty
            from menu_timeline
            """;

    public boolean isEmpty() {
        try (PreparedStatement statement = connection().prepareStatement(SELECT_IS_MENU_TIMELINE_EMPTY)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next(); // we expect here exactly one row in result set
            return resultSet.getBoolean("is_empty");
        } catch (SQLException e) {
            throw new StoreException("Error happened while trying run query checking if menu timeline is empty.", e);
        }
    }


    private static final String SELECT_FIRST_DATE_QUERY = """
            select min(menu_date) as first_date
            from menu_timeline
            """;

    public Optional<LocalDate> firstDate() {
        try (PreparedStatement statement = connection().prepareStatement(SELECT_FIRST_DATE_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(resultSet.getDate("first_date").toLocalDate());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new StoreException("Error happened, while executing query to get first date.", e);
        }
    }

    private static final String SELECT_LAST_DATE_QUERY = """
            select max(menu_date) as last_date
            from menu_timeline
            """;

    public Optional<LocalDate> lastDate() {
        try (PreparedStatement statement = connection().prepareStatement(SELECT_LAST_DATE_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(resultSet.getDate("last_date").toLocalDate());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new StoreException("Error happened while executing query to get last date.", e);
        }
    }


    private static final String HAS_MENU_FOR_DATE_QUERY = """
            select count(*) = 0 as has_menu
            from menu_timeline
            where menu_date = ?
            """;

    public boolean hasMenuFor(final LocalDate date) {
        try (PreparedStatement statement = connection().prepareStatement(HAS_MENU_FOR_DATE_QUERY)) {
            statement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("has_menu");
        } catch (SQLException e) {
            throw new StoreException("Error happened, while executing query to check if menu for date is present.", e);
        }
    }


    private static final String INSERT_DISH_QUERY = """
            insert into menu_timeline (menu_date, meal_time, dish_public_id, dish_position)
            values                    (        ?,         ?,              ?,             ?)
            """;

    public void acceptMenu(
            final DayMenu dayMenu,
            final MenuTimelineStoreEditable.OnAlreadyDefined onAlreadyDefined
    ) {
        final boolean autoCommitOld;
        try {
            autoCommitOld = connection().getAutoCommit();
        } catch (SQLException e) {
            throw new StoreException("Looks like some unexpected problem with database connection.", e);
        }

        try {
            connection().setAutoCommit(false);
            if (hasMenuFor(dayMenu.getDate())) {
                switch (onAlreadyDefined) {
                    case SKIP:
                        return;
                    case FAIL:
                        throw new StoreException("Menu for date " + dayMenu.getDate() + " already exists.");
                    case OVERWRITE:
                        removeMenu(dayMenu.getDate());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + onAlreadyDefined);
                }
            }

            try (PreparedStatement statement = connection().prepareStatement(INSERT_DISH_QUERY)) {
                for (var mealTime : MealTime.values()) {
                    int position = 1;
                    for (var dish : dayMenu.get(mealTime)) {
                        /*
                          insert into menu_timeline (menu_date, meal_time, dish_public_id, dish_position)
                          values                    (        ?,         ?,              ?,             ?)
                        */
                        statement.setDate(1, Date.valueOf(dayMenu.getDate()));
                        statement.setString(2, mealTime.name());
                        statement.setString(3, dish.publicId());
                        statement.setInt(4, position);
                        statement.executeUpdate();
                        position++;
                    }
                }
            }

            if (autoCommitOld) {
                connection().commit();
            } // else we let commit or rollback changes to the caller, who disabled auto-commit
        } catch (Exception e) {
            try {
                connection().rollback();
            } catch (Exception rollbackException) {
                LOGGER.error("acceptMenu(): Failed to rollback current changes. Another error happened.", rollbackException);
            }
        } finally {
            try {
                connection().setAutoCommit(autoCommitOld);
            } catch (Exception autoCommitException) {
                LOGGER.error("acceptMenu(): Failed to restore auto-commit value. Another error happened.", autoCommitException);
            }
        }
    }

    private static final String REMOVE_MENU_FOR_DATE_QUERY = """
            delete
            from menu_timeline
            where menu_date = ?
            """;

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeMenu(final LocalDate date) {
        try (PreparedStatement statement = connection().prepareStatement(REMOVE_MENU_FOR_DATE_QUERY)) {
            statement.setDate(1, Date.valueOf(date));
            final int affectedRowsCount = statement.executeUpdate();
            return affectedRowsCount > 0;
        } catch (SQLException e) {
            throw new StoreException("Error happened, while trying to delete menu_timeline records for date " + date.toString(), e);
        }
    }

    public boolean getAutoCommit() throws SQLException {
        return connection().getAutoCommit();
    }

    public void setAutoCommit(final boolean value) throws SQLException {
        connection().setAutoCommit(value);
    }

    private Connection connection() {
        return this.connection;
    }

    public void rollback() throws SQLException {
        connection().rollback();
    }

    public void commit() throws SQLException {
        connection().commit();
    }
}
