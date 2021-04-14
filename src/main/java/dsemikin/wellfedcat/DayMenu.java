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
}
