package xyz.dsemikin.wellfedcat.datamodel;

import java.util.List;
import java.util.Optional;

public interface DishStore {
    List<Dish> allDishes();
    Optional<Dish> dish(final String name);

    void addDish(final Dish dish); // may be not implemented
    void removeDish(final String name); // maybe not implemented
}
