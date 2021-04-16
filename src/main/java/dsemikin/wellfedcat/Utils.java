package dsemikin.wellfedcat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Utils {

    public static void printDishes(final List<Dish> dishes) {
        for (Dish dish : dishes) {
            System.out.println(dish.toString());
        }
    }

    public static void printMenu(final Menu menu) {
        for (DayMenu dayMenu : menu) {
            StringBuilder stringBuilder = new StringBuilder();
            Optional<LocalDate> maybeDate = dayMenu.getMaybeDate();
            if (maybeDate.isPresent()) {
                LocalDate date = maybeDate.get();
                stringBuilder.append(date).append(" - ").append(date.getDayOfWeek());
            } else if (dayMenu.getMaybeDayOfWeek().isPresent()) {
                stringBuilder.append(dayMenu.getMaybeDayOfWeek().get());
            }
            System.out.println(stringBuilder);

            System.out.println();
            System.out.println("Breakfast:");
            printDishes(dayMenu.getBreakfast());

            System.out.println();
            System.out.println("Lunch:");
            printDishes(dayMenu.getLunch());

            System.out.println();
            System.out.println("Supper:");
            printDishes(dayMenu.getSupper());

            System.out.println();
            System.out.println();
        }
    }
}
