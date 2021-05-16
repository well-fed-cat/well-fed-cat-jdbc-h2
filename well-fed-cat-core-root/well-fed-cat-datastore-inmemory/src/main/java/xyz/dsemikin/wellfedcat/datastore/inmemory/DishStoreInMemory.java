package xyz.dsemikin.wellfedcat.datastore.inmemory;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DishStoreInMemory
        implements DishStoreEditable, Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, Dish> dishes;

    public DishStoreInMemory() {
        dishes = new HashMap<>();
    }

    @Override
    public List<Dish> all() {
        return new ArrayList<>(dishes.values());
    }

    @Override
    public Optional<Dish> get(String name) {
        return Optional.ofNullable(dishes.get(name));
    }

    @Override
    public boolean add(Dish dish) {
        if (dishes.containsKey(dish.name())) {
            return false;
        }
        dishes.put(dish.name(), dish);
        return true;
    }

    @Override
    public RemoveStatus remove(String name) {
        Dish removedDish = dishes.remove(name);
        return removedDish == null ? RemoveStatus.DOES_NOT_EXIST : RemoveStatus.SUCCESS;
    }
}
