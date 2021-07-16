package xyz.dsemikin.wellfedcat.datastore.db.h2;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishModified;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datamodel.StoreException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("ClassCanBeRecord")
public class DishStoreDbH2 implements
        DishStoreEditable
{
    private final DishDao dishDao;

    public DishStoreDbH2(final DishDao dishDao) {
        this.dishDao = dishDao;
    }

    @Override
    public List<Dish> all() {
        try {
            return dishDao.all();
        } catch (SQLException e) {
            throw new StoreException("Failed to get all dishes.", e);
        }
    }

    @Override
    public Optional<Dish> getByName(final String name) {
        try {
            return dishDao.getByName(name);
        } catch (SQLException e) {
            throw new StoreException("Failed to get the dish.", e);
        }
    }

    @Override
    public Optional<Dish> getById(String publicId) {
        return dishDao.getById(publicId);
    }

    @Override
    public Optional<Dish> create(String publicId, String name, Set<MealTime> suitableForMealTimes) {
        // TODO
        throw new RuntimeException("Not Implemented yet");
    }

    @Override
    public DeleteStatus deleteByName(final String name) {
        try {
            return dishDao.removeByName(name);
        } catch (SQLException e) {
            throw new StoreException("Failed to remove dish", e);
        }
    }

    @Override
    public DeleteStatus deleteByPublicId(String publicId) {
        try {
            return dishDao.removeById(publicId);
        } catch (SQLException e) {
            throw new StoreException("Failed to remote dish", e);
        }
    }

    @Override
    public DeleteStatus deleteByStrongId(UUID strongId) {
        return null;
    }

    @Override
    public UpdateStatus updateDish(DishModified newDishVersion) {
        // TODO
        throw new RuntimeException("Not implemented yet");
    }
}
