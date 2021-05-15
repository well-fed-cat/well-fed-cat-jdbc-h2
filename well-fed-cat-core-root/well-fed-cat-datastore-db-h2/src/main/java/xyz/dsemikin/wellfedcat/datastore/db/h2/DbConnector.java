package xyz.dsemikin.wellfedcat.datastore.db.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbConnector.class);

    private final Connection connection;

    public DbConnector(final String dbFilePath) throws SQLException {
        // TODO: dbFilePath is not actually a real path. The file name in it is without extension
        // TODO: solution below fails, if input has form "~/something". How to solve it?
//        final String fullDbFilePath = Paths.get(dbFilePath).toAbsolutePath().normalize().toString();
        final String fullDbFilePath = dbFilePath;
        final String connectionUrl = "jdbc:h2:" + fullDbFilePath;
        connection = DriverManager.getConnection(connectionUrl, "sa", "sa");
    }

    public Connection connection() {
        return connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Failed to close DB connection.", e);
        }
    }
}
