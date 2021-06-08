package xyz.dsemikin.wellfedcat.datamodel;

import xyz.dsemikin.wellfedcat.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiConsumer;

/** Day menu without date. */
public class DayMenuSimple implements Serializable {

    private LinkedHashSet<Dish> breakfast;
    private LinkedHashSet<Dish> lunch;
    private LinkedHashSet<Dish> supper;

    public DayMenuSimple() {
        breakfast = new LinkedHashSet<>();
        lunch = new LinkedHashSet<>();
        supper = new LinkedHashSet<>();
    }

    public LinkedHashSet<Dish> getBreakfast() {
        return breakfast;
    }

    public LinkedHashSet<Dish> getLunch() {
        return lunch;
    }

    public LinkedHashSet<Dish> getSupper() {
        return supper;
    }

    public void setBreakfast(final List<Dish> breakfast) {
        this.breakfast = new LinkedHashSet<>(breakfast);
    }

    public void setBreakfast(final Dish breakfastDish) {
        breakfast = new LinkedHashSet<>(singletonArrayList(breakfastDish));
    }

    public void setLunch(final List<Dish> lunch) {
        this.lunch = new LinkedHashSet<>(lunch);
    }

    public void setLunch(final Dish lunchDish) {
        lunch = new LinkedHashSet<>(singletonArrayList(lunchDish));
    }

    public void setSupper(List<Dish> supper) {
        this.supper = new LinkedHashSet<>(supper);
    }

    public void setSupper(final Dish supperDish) {
        supper = new LinkedHashSet<>(singletonArrayList(supperDish));
    }

    public boolean isEmpty() {
        return breakfast.isEmpty() && lunch.isEmpty() && supper.isEmpty();
    }

    private ArrayList<Dish> singletonArrayList(final Dish dish) {
        return new ArrayList<>(Collections.singletonList(dish));
    }

    public LinkedHashSet<Dish> get(final MealTime mealTime) {
        return switch (mealTime) {
            case BREAKFAST -> breakfast;
            case LUNCH -> lunch;
            case SUPPER -> supper;
        };
    }

    @Override
    public String toString() {
        BiConsumer<Dish, StringBuilder> appendDish =
                (final Dish dish, final StringBuilder sb) -> {
                    sb
                            .append("[").append(dish.publicId()).append("] ")
                            .append(Utils.translit(dish.name())).append(", ");
                    sb.delete(sb.length()-2, sb.length());
                };

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("B:  ");
        getBreakfast().forEach(dish -> appendDish.accept(dish, stringBuilder));
        stringBuilder.append("\n");

        stringBuilder.append("L:  ");
        getLunch().forEach(dish -> appendDish.accept(dish, stringBuilder));
        stringBuilder.append("\n");

        stringBuilder.append("S:  ");
        getSupper().forEach(dish -> appendDish.accept(dish, stringBuilder));
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
