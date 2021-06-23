package xyz.dsemikin.wellfedcat.datamodel;

import xyz.dsemikin.wellfedcat.utils.Utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public record Dish(
        String publicId,
        String name,
        Set<MealTime> suitableForMealTimes
)
    implements Serializable
{

    public static final int MAX_DISH_NAME_LENGTH = 100; // must be coherent with DB schema

    public static Dish make(final String publicId, final String name, MealTime... mealTimes) {
        return new Dish(publicId, name, new LinkedHashSet<>(Arrays.asList(mealTimes)));
    }

    public Dish {
        // TODO: Check, that arguments are not empty
        final boolean publicIdIsOk =
                publicId.codePoints().allMatch(c ->
                        c < 128 &&
                                (Character.isLetter(c)
                                        || Character.isDigit(c)
                                        || c == Character.codePointAt("_", 0)
                                )
                );
        if (!publicIdIsOk) {
            throw new IllegalArgumentException("publicId must be composed of digits, ascii chars or underscore.");
        }

        if (name.length() > MAX_DISH_NAME_LENGTH) {
            throw new IllegalArgumentException("Max allowed length of dish name is " + MAX_DISH_NAME_LENGTH);
        }
    }

    @Override
    public String toString() {
        final StringBuilder string = new StringBuilder(publicId() + " : " + Utils.translit(name()) + " : ");
        for (var mealTime : suitableForMealTimes()) {
            string.append(mealTime).append(", ");
        }
        string.delete(string.length()-2, string.length());
        return string.toString();
    }
}
