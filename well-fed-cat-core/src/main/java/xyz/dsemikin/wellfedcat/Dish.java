package xyz.dsemikin.wellfedcat;

import java.io.Serializable;
import java.util.Set;

public record Dish(
        String name,
        Set<MealTime> suitableForMealTimes
)
    implements Serializable
{
    // nothing here
}
