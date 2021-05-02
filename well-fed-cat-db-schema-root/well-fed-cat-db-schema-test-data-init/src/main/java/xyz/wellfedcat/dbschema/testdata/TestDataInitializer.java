package xyz.wellfedcat.dbschema.testdata;

import org.h2.tools.RunScript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDataInitializer {

    public static void main(String[] args) {
        TestDataInitializer initializer = new TestDataInitializer();
        initializer.initTestData();
    }

    public void initTestData() {
        try (
            Connection connection = DriverManager.getConnection ("jdbc:h2:~/test", "sa","");
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sql/init-test-data.sql")));
        ) {
            RunScript.execute(connection, reader);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to run SQL script.", e);
        }
    }
}
