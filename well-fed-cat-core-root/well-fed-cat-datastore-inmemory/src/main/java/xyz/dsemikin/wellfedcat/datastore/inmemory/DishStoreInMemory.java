package xyz.dsemikin.wellfedcat.datastore.inmemory;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStore;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DishStoreInMemory
        implements DishStore, Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, Dish> dishes;

    public DishStoreInMemory() {
        dishes = new HashMap<>();
    }

    @Override
    public List<Dish> allDishes() {
        return new ArrayList<>(dishes.values());
    }

    @Override
    public Optional<Dish> dish(String name) {
        return Optional.ofNullable(dishes.get(name));
    }

    @Override
    public void addDish(Dish dish) {
        dishes.put(dish.name(), dish);
    }

    @Override
    public void removeDish(String name) {
        dishes.remove(name);
    }
}
