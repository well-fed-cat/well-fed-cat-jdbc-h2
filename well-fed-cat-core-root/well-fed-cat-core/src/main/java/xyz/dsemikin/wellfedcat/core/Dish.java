package xyz.dsemikin.wellfedcat.core;

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
