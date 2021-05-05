package xyz.dsemikin.wellfedcat.datastore.db.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbConnector.class);

    private static final String DBNAME = "wellfedcat";
    private final Connection connection;

    public DbConnector(final String dbFilePath) throws SQLException {
        final String fullDbFilePath = Paths.get(dbFilePath).toAbsolutePath().normalize().toString();
        final String connectionUrl = "jdbc:h2:" + fullDbFilePath + "/" + DBNAME;
        connection = DriverManager.getConnection(connectionUrl, "sa", "sa");
    }

    public Connection connection() {
        return connection;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Failed to close DB connection.", e);
        }
    }
}
