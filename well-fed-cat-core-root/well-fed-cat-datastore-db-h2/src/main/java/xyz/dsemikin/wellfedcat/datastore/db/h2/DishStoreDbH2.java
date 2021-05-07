package xyz.dsemikin.wellfedcat.datastore.db.h2;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStore;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DishStoreDbH2 implements
        DishStore,
        Closeable
{
    private final DbConnector dbConnector;
    private final DishDao dishDao;

    public DishStoreDbH2(final String dbFilePath) {
        try {
            dbConnector = new DbConnector(dbFilePath);
            dishDao = new DishDao(dbConnector.connection());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database.", e);
        }
    }

    @Override
    public List<Dish> allDishes() {
        return dishDao.allDishes();
    }

    @Override
    public Optional<Dish> dish(final String name) {
        return dishDao.dish(name);
    }

    @Override
    public void addDish(final Dish dish) {
        dishDao.addDish(dish);
    }

    @Override
    public void removeDish(final String name) {
        dishDao.removeDish(name);
    }

    @Override
    public void close() throws IOException {
        dbConnector.close();
    }
}
