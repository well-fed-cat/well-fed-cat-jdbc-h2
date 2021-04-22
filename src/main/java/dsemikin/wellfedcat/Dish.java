package dsemikin.wellfedcat;

import java.util.Set;

public record Dish(
        String name,
        Set<MealTime> suitableForMealTimes
) {
    // nothing here
}
