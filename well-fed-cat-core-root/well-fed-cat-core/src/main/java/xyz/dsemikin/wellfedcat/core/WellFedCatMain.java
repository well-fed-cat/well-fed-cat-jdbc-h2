package xyz.dsemikin.wellfedcat.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class WellFedCatMain {

    public static void main(String[] args) {
        final Path dishStoreFile = Paths.get("C:\\tmp\\WellFedCatDishStore.wds");
        final DishStore dishStore = DishStoreProvider.getDishStoreSimpleFileAndFillWithSampleData(dishStoreFile);
        final var menuManager = new MenuManager(dishStore);
        final Menu menu = menuManager.generateWeekMenu(LocalDate.now());
        Utils.printMenu(menu);
    }
}
