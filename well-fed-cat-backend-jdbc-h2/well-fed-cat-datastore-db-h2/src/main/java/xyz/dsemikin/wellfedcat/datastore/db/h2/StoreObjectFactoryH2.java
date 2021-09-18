package xyz.dsemikin.wellfedcat.datastore.db.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Only one object of this class may exist.
 *
 * We make it instantiatable to be able to use Closeable
 * interface to explicitly manage releasing of resources.
 */
public class StoreObjectFactoryH2 implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreObjectFactoryH2.class);

    private static boolean instanceExists = false;

    private final Connection connection;
    private boolean isClosed = false;

    public StoreObjectFactoryH2(final String dbFilePath) throws SQLException {

        if (instanceExists) {
            throw new IllegalStateException("New instances of this class may be created only after all other instnces are closed.");
        }
        instanceExists = true;

        // TODO: dbFilePath is not actually a real path. The file name in it is without extension
        // TODO: solution below fails, if input has form "~/something". How to solve it?
//        final String fullDbFilePath = Paths.get(dbFilePath).toAbsolutePath().normalize().toString();
        final String fullDbFilePath = dbFilePath;
        final String connectionUrl = "jdbc:h2:" + fullDbFilePath;
        connection = DriverManager.getConnection(connectionUrl, "sa", "sa");
    }

    public DishStoreDbH2 createDishStore() {
        DishDao dishDao = new DishDao(connection);
        return new DishStoreDbH2(dishDao);
    }

    public MenuTimelineStoreH2 createMenuTimelineStore() {
        final MenuTimelineDao menuTimelineDao = new MenuTimelineDao(connection);
        final DishDao dishDao = new DishDao(connection);
        final MenuTimelineService menuTimelineService = new MenuTimelineService(menuTimelineDao, dishDao);
        return new MenuTimelineStoreH2(menuTimelineService);
    }

    /** After close() is called dishStore and menuTimelineStore created by this instance cannot be used anymore. */
    @Override
    public void close() {
        if (!isClosed) {
            // Calling "close()" on instance, which is already closed
            // should not set `instanceExists` (probably tracking another
            // instance) to false.
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close DB connection.", e);
            } finally {
                instanceExists = false;
                isClosed = true;
            }
        }
    }
}
