package xyz.dsemikin.wellfedcat.app.sample.simplefile;

import xyz.dsemikin.wellfedcat.core.DishStoreProvider;
import xyz.dsemikin.wellfedcat.core.Menu;
import xyz.dsemikin.wellfedcat.core.MenuManager;
import xyz.dsemikin.wellfedcat.core.Utils;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class WellFedCatMain {

    public static void main(String[] args) throws DishStoreException {
        final Path dishStoreFile = Paths.get("C:\\tmp\\WellFedCatDishStore.wds");
        final DishStoreEditable dishStore = DishStoreProvider.getDishStoreSimpleFileAndFillWithSampleData(dishStoreFile);
        final var menuManager = new MenuManager(dishStore);
        final Menu menu = menuManager.generateWeekMenu(LocalDate.now());
        Utils.printMenu(menu);
    }
}
