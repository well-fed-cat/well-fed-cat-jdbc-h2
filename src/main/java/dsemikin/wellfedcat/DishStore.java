package dsemikin.wellfedcat;

import java.util.List;

public interface DishStore {
    List<Dish> allDishes();
    Dish dish(final String name);

    void addDish(final Dish dish); // may be not implemented
    void removeDish(final String name); // maybe not implemented
}
