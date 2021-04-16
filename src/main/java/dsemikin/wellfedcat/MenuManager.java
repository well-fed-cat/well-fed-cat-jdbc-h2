package dsemikin.wellfedcat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MenuManager {

    public static void fillMenu(final Menu menu) {
        Optional<Dish> previousDish = Optional.empty();
        Set<Dish> usedDishes = new HashSet<>();
        for (DayMenu dayMenu : menu) {
            Dish breakfast = pickNextDish(previousDish, usedDishes);
            dayMenu.setBreakfast(breakfast);
            usedDishes.add(breakfast);
            previousDish = Optional.of(breakfast);

            Dish lunch = pickNextDish(previousDish, usedDishes);
            dayMenu.setLunch(lunch);
            usedDishes.add(lunch);
            previousDish = Optional.of(lunch);

            Dish supper = pickNextDish(previousDish, usedDishes);
            dayMenu.setSupper(supper);
            usedDishes.add(lunch);
            previousDish = Optional.of(supper);
        }
    }

    private static Dish pickNextDish(
            final Optional<Dish> previousDish,
            final Collection<Dish> usedDishes
    ) {
        // TODO: Implement this
        return new Dish("noname");
    }

    public static Menu generateMenu(final int daysCount) {
        Menu menu = new Menu();
        for (int kk = 0; kk < daysCount; kk++) {
            menu.add(new DayMenu());
        }
        return menu;
    }

    public static Menu generateMenu(final DayOfWeek startDay, final int daysCount) {
        Menu menu = new Menu();
        DayOfWeek currentDay = startDay;
        for (int kk = 0; kk < daysCount; kk++) {
            menu.add(new DayMenu(currentDay));
            currentDay = currentDay.plus(1);
        }
        return menu;
    }

    public static Menu generateMenu(final LocalDate startDay, final int daysCount) {
        Menu menu = new Menu();
        LocalDate currentDay = startDay;
        for (int kk = 0; kk < daysCount; kk++) {
            menu.add(new DayMenu(currentDay));
            currentDay = currentDay.plusDays(1);
        }
        return menu;
    }

    public static Menu generateWeekMenu(final LocalDate startDay) {
        return generateMenu(startDay, 7);
    }
}
