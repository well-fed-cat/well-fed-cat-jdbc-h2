package xyz.dsemikin.wellfedcat.datamodel;

import java.util.List;
import java.util.Optional;

public interface DishStoreEditable extends DishStore {

    void addDish(final Dish dish) throws DishStoreException;
    void removeDish(final String name) throws DishStoreException;

}
