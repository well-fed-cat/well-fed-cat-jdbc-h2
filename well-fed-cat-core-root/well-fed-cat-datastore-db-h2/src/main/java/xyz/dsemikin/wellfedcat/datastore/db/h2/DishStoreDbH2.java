package xyz.dsemikin.wellfedcat.datastore.db.h2;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreException;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DishStoreDbH2 implements
        DishStoreEditable,
        Closeable
{
    private final DbConnector dbConnector;
    private final DishDao dishDao;

    public DishStoreDbH2(final String dbFilePath) {
        try {
            dbConnector = new DbConnector(dbFilePath);
            dishDao = new DishDao(dbConnector.connection());
        } catch (SQLException e) {
            throw new DishStoreException("Failed to connect to database.", e);
        }
    }

    @Override
    public List<Dish> all() {
        try {
            return dishDao.all();
        } catch (SQLException e) {
            throw new DishStoreException("Failed to get all dishes.", e);
        }
    }

    @Override
    public Optional<Dish> getByName(final String name) {
        try {
            return dishDao.getByName(name);
        } catch (SQLException e) {
            throw new DishStoreException("Failed to get the dish.", e);
        }
    }

    @Override
    public Optional<Dish> getById(String publicId) {
        try {
            return dishDao.getById(publicId);
        } catch (SQLException e) {
            throw new DishStoreException("Failed to get the dish", e);
        }
    }

    @Override
    public boolean add(final Dish dish) {
        try {
            return dishDao.add(dish);
        } catch (SQLException e) {
            throw new DishStoreException("Failed to add dish.", e);
        }
    }

    @Override
    public RemoveStatus removeByName(final String name) {
        try {
            return dishDao.removeByName(name);
        } catch (SQLException e) {
            throw new DishStoreException("Failed to remove dish", e);
        }
    }

    @Override
    public RemoveStatus removeById(String publicId) {
        try {
            return dishDao.removeById(publicId);
        } catch (SQLException e) {
            throw new DishStoreException("Failed to remote dish", e);
        }
    }

    @Override
    public void close() {
        dbConnector.close();
    }
}
