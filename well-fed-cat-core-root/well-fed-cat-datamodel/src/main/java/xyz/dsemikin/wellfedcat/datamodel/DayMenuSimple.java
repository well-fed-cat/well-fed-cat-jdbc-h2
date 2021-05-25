package xyz.dsemikin.wellfedcat.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Day menu without date. */
public class DayMenuSimple {

    private List<Dish> breakfast;
    private List<Dish> lunch;
    private List<Dish> supper;

    public DayMenuSimple() {
        breakfast = new ArrayList<>();
        lunch = new ArrayList<>();
        supper = new ArrayList<>();
    }

    public List<Dish> getBreakfast() {
        return breakfast;
    }

    public List<Dish> getLunch() {
        return lunch;
    }

    public List<Dish> getSupper() {
        return supper;
    }

    public void setBreakfast(final List<Dish> breakfast) {
        this.breakfast = breakfast;
    }

    public void setBreakfast(final Dish breakfastDish) {
        breakfast = singletonArrayList(breakfastDish);
    }

    public void setLunch(final List<Dish> lunch) {
        this.lunch = lunch;
    }

    public void setLunch(final Dish lunchDish) {
        lunch = singletonArrayList(lunchDish);
    }

    public void setSupper(List<Dish> supper) {
        this.supper = supper;
    }

    public void setSupper(final Dish supperDish) {
        supper = singletonArrayList(supperDish);
    }

    private ArrayList<Dish> singletonArrayList(final Dish dish) {
        return new ArrayList<>(Collections.singletonList(dish));
    }
}
