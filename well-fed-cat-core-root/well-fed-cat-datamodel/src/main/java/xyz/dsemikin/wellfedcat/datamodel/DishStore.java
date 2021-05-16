package xyz.dsemikin.wellfedcat.datamodel;

import java.util.List;
import java.util.Optional;

public interface DishStore {

    /** Returns all dishes in store as a list
     *
     * @return List of all dishes in the store. List is empty if
     *         store is empty.
     *
     * @throws DishStoreException - Implementation specific.
     */
    List<Dish> all();

    /** Get dish by name.
     *
     * @param name - name of the dish to get.
     *
     * @return - Empty `Optional`, if store does not contain dish with
     *           such a name. Otherwise `Optional` filled with found
     *           object.
     *
     * @throws DishStoreException - Implementation specific.
     */
    Optional<Dish> get(final String name);

}
