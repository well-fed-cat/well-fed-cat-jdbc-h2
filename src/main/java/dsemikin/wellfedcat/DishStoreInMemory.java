package dsemikin.wellfedcat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishStoreInMemory implements DishStore {

    private final Map<String, Dish> dishes;

    public DishStoreInMemory() {
        dishes = new HashMap<>();
    }

    @Override
    public List<Dish> allDishes() {
        return new ArrayList<>(dishes.values());
    }

    @Override
    public Dish dish(String name) {
        return dishes.get(name);
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
