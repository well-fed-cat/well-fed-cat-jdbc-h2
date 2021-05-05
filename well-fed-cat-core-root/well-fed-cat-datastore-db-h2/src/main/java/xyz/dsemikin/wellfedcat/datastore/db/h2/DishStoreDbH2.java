package xyz.dsemikin.wellfedcat.datastore.db.h2;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStore;

import java.util.List;

public class DishStoreDbH2 implements DishStore {
    @Override
    public List<Dish> allDishes() {
        return null;
    }

    @Override
    public Dish dish(String name) {
        return null;
    }

    @Override
    public void addDish(Dish dish) {

    }

    @Override
    public void removeDish(String name) {

    }
}
