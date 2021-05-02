package xyz.dsemikin.wellfedcat.dbschema.updater;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;

public class DbSchemaUpdater {
    public static void main(String[] args) {
        final String dbUrl = "jdbc:h2:~/test_db";
        final String user = "sa";
        final String password = "sa";
        Flyway flyway = Flyway.configure()
                .dataSource(dbUrl, user, password)
                .locations("classpath:sql")
                .load();

        flyway.migrate();

        MigrationInfoService infoService = flyway.info();

        for (MigrationInfo info : infoService.all()) {
            System.out.println("Version: " + info.getVersion()
                    + "; description: " + info.getDescription()
                    + "; state: " + info.getState()
            );
        }
    }
}
