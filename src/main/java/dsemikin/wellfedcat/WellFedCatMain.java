package dsemikin.wellfedcat;

import java.time.LocalDate;

public class WellFedCatMain {

    public static void main(String[] args) {
        var menuManager = new MenuManager();
        Menu menu = menuManager.generateWeekMenu(LocalDate.now());
        Utils.printMenu(menu);
    }
}
