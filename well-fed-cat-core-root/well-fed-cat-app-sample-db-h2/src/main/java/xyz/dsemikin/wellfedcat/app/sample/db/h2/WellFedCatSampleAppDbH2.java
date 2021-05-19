package xyz.dsemikin.wellfedcat.app.sample.db.h2;

import xyz.dsemikin.wellfedcat.core.Menu;
import xyz.dsemikin.wellfedcat.core.MenuManager;
import xyz.dsemikin.wellfedcat.core.Utils;
import xyz.dsemikin.wellfedcat.datastore.db.h2.DishStoreDbH2;

import java.time.LocalDate;

public class WellFedCatSampleAppDbH2 {
    public static void main(String[] args) {
        final String dbFilePath = "~/wellfedcat_db";
        final var dishStore = new DishStoreDbH2(dbFilePath);
        final var menuManager = new MenuManager(dishStore);
        final Menu menu = menuManager.generateWeekMenu(LocalDate.now());
        Utils.printMenuT(menu);
    }

}
