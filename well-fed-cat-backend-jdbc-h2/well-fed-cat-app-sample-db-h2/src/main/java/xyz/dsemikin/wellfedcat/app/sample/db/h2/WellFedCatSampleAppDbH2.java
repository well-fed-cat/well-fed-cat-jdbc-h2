package xyz.dsemikin.wellfedcat.app.sample.db.h2;

import xyz.dsemikin.wellfedcat.core.DishUtils;
import xyz.dsemikin.wellfedcat.core.MenuManager;
import xyz.dsemikin.wellfedcat.datamodel.Menu;
import xyz.dsemikin.wellfedcat.datastore.db.h2.StoreObjectFactoryH2;

import java.sql.SQLException;
import java.time.LocalDate;

public class WellFedCatSampleAppDbH2 {
    public static void main(String[] args) throws SQLException {
        final String dbFilePath = "~/wellfedcat_db";
        try (StoreObjectFactoryH2 storeObjectFactoryH2 = new StoreObjectFactoryH2(dbFilePath)) {
            final var dishStore = storeObjectFactoryH2.createDishStore();
            final var menuManager = new MenuManager(dishStore);
            final Menu menu = menuManager.generateWeekMenu(LocalDate.now());
            DishUtils.printMenuT(menu);
        }
    }

}
