package xyz.wellfedcat.dbschema.testdata;

import org.h2.tools.RunScript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class TestDataInitializer {

    public static void main(String[] args) {
        TestDataInitializer initializer = new TestDataInitializer();
        initializer.initTestData();
    }

    public void initTestData() {
        try (
                Connection connection = DriverManager.getConnection ("jdbc:h2:~/wellfedcat_db", "sa","sa");
                InputStream inputStream = Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("sql/init-test-data.sql")
                );
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        ) {
            RunScript.execute(connection, reader);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to run SQL script.", e);
        }
    }
}
