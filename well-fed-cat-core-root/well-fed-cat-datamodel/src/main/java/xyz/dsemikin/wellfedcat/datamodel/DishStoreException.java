package xyz.dsemikin.wellfedcat.datamodel;

/** Base class for all exceptions trhown from `DishStore` implementations.
 *
 * It can also be used directly.
 */
public class DishStoreException extends RuntimeException {

    public DishStoreException(final String message) {
        super(message);
    }

    public DishStoreException(final String message, final Throwable reason) {
        super(message, reason);
    }
}
