package xyz.dsemikin.wellfedcat.datamodel;

public interface DishStoreEditable extends DishStore {

    /** Add dish to store if possible.
     *
     * If dish cannot be add because it violates some "unique"-
     * constraint (e.g. this dish-name is already used), then
     * dish is not added and `false` is returned.
     *
     * @param dish - Definition of dish to add to the store.
     *
     * @return - `true` if dish was successfully added.
     *           `false` if some "unique"-constraint was
     *           violated and thus the dish was not added
     *           (e.g. another dish with this name already
     *           exists. Later another constraints may be
     *           added to the model). If dish was not added
     *           because another problem happened, exception
     *           will be thrown (implementation specific).
     */
    boolean add(final Dish dish);

    /** Remove dish from store, if possible.
     *
     * @param name - name of the dish to be removed from store.
     * @return - `SUCCESS`, if dish was removed,
     *           `DOES_NOT_EXIST`, if dish with given name does not
     *           exist, and `CANNOT_BE_DELETED`, if dish cannot be
     *           deleted because it is used in menu timeline store.
     */
    RemoveStatus removeByName(final String name);

    RemoveStatus removeById(final String publicId);

    // TODO: updateDish (overwrites existing dish with the same publicId).
    // TODO: For this we need Dish versioning, "dirty" and "renaming" for dishes.

    enum RemoveStatus {
        /** Dish was successfully removed. */
        SUCCESS,
        /** Dish was not removed, because it does not exist. */
        DOES_NOT_EXIST,
        /** Dish was not removed, because it referenced (used) in menu timeline store. */
        USED_IN_MENU_TIMELINE
    }

}
