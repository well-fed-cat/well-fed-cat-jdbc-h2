package xyz.dsemikin.wellfedcat.datamodel;

/**
 * <p>
 *     Any implementation should respect these additional requirements:
 * </p>
 *
 * <ul>
 *     <li>
 *         If the dish with particular public id and name was deleted, it
 *         should be possible to use them again (independently) for other
 *         dishes.
 *     </li>
 *     <li>
 *         Same is valid, if dish name or public id was changed, - the old
 *         value is allowed to be reused by other dishes.
 *     </li>
 * </ul>
 *
 * <h3>Interaction with {@code MenuTimelineStore}</h3>
 * <p>
 *     Even though {@code DishStoreEditable} does not explicitly refer to
 *     {@link MenuTimelineStore} or {@link MenuTimelineStoreEditable}, their
 *     behavior together should be explicitly specified to ensure consistent
 *     behavior of different implementations of these stores.
 *
 *     See documentation for {@link MenuTimelineStoreEditable} for additional
 *     constraints interaction of this class with {@link MenuTimelineStore}
 *     and {@link MenuTimelineStoreEditable}.
 * </p>
 */
public interface DishStoreEditable extends DishStore {

    /** Add dish to store if possible.
     *
     * If dish cannot be add because it violates some "unique"-
     * constraint (e.g. this dish-name is already used), then
     * dish is not added and `false` is returned.
     *
     * @param dish  Definition of dish to add to the store.
     *
     * @return  {@code true} if dish was successfully added.
     *          {@code false} if some "unique"-constraint was
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
     * @param name   name of the dish to be removed from store.
     * @return   {@link RemoveStatus#SUCCESS}, if dish was removed,
     *           {@link RemoveStatus#DOES_NOT_EXIST}, if dish with given name
     *           does not exist, and
     *           {@link RemoveStatus#USED_IN_MENU_TIMELINE}, if dish cannot
     *           be deleted because it is used in menu timeline store.
     *           If dish cannot be deleted because of some other reason,
     *           some exception will be thrown (implementation specific).
     */
    RemoveStatus removeByName(final String name);

    /** Remove dish from store, if possible.
     *
     * @param publicId   public id of the dish to be removed from store.
     * @return   {@link RemoveStatus#SUCCESS}, if dish was removed,
     *           {@link RemoveStatus#DOES_NOT_EXIST}, if dish with given name
     *           does not exist, and
     *           {@link RemoveStatus#USED_IN_MENU_TIMELINE}, if dish cannot
     *           be deleted because it is used in menu timeline store.
     *           If dish cannot be deleted because of some other reason,
     *           some exception will be thrown (implementation specific).
     */
    RemoveStatus removeById(final String publicId);

    /**
     * Using this method it is possible to change some parameters of the dish.
     *
     * For this one needs to get dish from store and then modify it using
     * "update" methods and then update it in the store using this method.
     *
     * If it is for some reason not possible to update the dish, then
     * implementation specific exceptions may be thrown.
     *
     * Implementation should use {@link DishModified#oldPublicId()} to
     * search for the dish in the store, which is to be updated.
     *
     * @param newDishVersion  updated version of the dish. Obtain by
     *                        getting dish from the store and then using
     *                        "update"-methods on it.
     * @return See description of the {@link UpdateStatus} values.
     */
    UpdateStatus updateDish(final DishModified newDishVersion);

    enum RemoveStatus {
        /** Dish was successfully removed. */
        SUCCESS,
        /** Dish was not removed, because it does not exist. */
        DOES_NOT_EXIST,
        /** Dish was not removed, because it is referenced (used) in menu timeline store. */
        USED_IN_MENU_TIMELINE
    }

    enum UpdateStatus {
        /** Dish was updated successfully. */
        SUCCESS,
        /**
         * Version of the new dish must be version of the original dish +1.
         * This code is returned, if this condition does not hold (usually
         * happens, if tried to update from the same version more than one
         * time).
         */
        VERSION_MISMATCH,
        /**
         * Dish with the same public id was not found in the store.
         * Probably it was deleted.
         */
        NOT_FOUND
    }

}
