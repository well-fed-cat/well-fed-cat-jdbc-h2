package dsemikin.wellfedcat;

import java.util.List;

public class Utils {
    public static void printDishes(List<Dish> dishes) {
        for (Dish dish : dishes) {
            System.out.println(dish.toString());
        }
    }
}
