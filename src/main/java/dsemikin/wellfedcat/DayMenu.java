package dsemikin.wellfedcat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DayMenu {

    private List<Dish> breakfast;
    private List<Dish> lunch;
    private List<Dish> supper;
    private Optional<LocalDate> maybeDate;
    private Optional<DayOfWeek> maybeDayOfWeek;

    public DayMenu() {
        breakfast = new ArrayList<>();
        lunch = new ArrayList<>();
        supper = new ArrayList<>();
        maybeDate = Optional.empty();
        maybeDayOfWeek = Optional.empty();
    }

    public DayMenu(final LocalDate date) {
        this();
        setDate(date);
    }

    public DayMenu(final DayOfWeek dayOfWeek) {
        this();
        setDayOfWeek(dayOfWeek);
    }

    /** Sets `date` of this object to given `date` and `dayOfWeek` of this object to corresponding day of week. */
    public void setDate(final LocalDate date) {
        maybeDate = Optional.of(date);
        maybeDayOfWeek = Optional.of(date.getDayOfWeek());
    }

    /** Remove definition of date and sets day of week to given value. */
    public void setDayOfWeek(final DayOfWeek dayOfWeek) {
        maybeDate = Optional.empty();
        maybeDayOfWeek = Optional.of(dayOfWeek);
    }

    public Optional<DayOfWeek> getMaybeDayOfWeek() {
        return maybeDayOfWeek;
    }

    public Optional<LocalDate> getMaybeDate() {
        return maybeDate;
    }

    public void resetDate() {
        maybeDate = Optional.empty();
    }

    public void resetDateAndDayOfWeek() {
        maybeDate = Optional.empty();
        maybeDayOfWeek = Optional.empty();
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

    public void setLunch(final List<Dish> lunch) {
        this.lunch = lunch;
    }

    public void setSupper(List<Dish> supper) {
        this.supper = supper;
    }
}
