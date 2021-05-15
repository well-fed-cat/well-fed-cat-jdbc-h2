package xyz.dsemikin.wellfedcat.datamodel;

import java.util.List;
import java.util.Optional;

public interface DishStore {

    List<Dish> allDishes() throws DishStoreException;
    Optional<Dish> dish(final String name) throws DishStoreException;

}
