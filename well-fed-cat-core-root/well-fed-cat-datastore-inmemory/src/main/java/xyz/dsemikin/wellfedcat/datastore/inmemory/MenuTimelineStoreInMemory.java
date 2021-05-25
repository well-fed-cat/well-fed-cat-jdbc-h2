package xyz.dsemikin.wellfedcat.datastore.inmemory;

import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.Menu;
import xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStoreEditable;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuTimelineStoreInMemory implements MenuTimelineStoreEditable {

    private final Map<LocalDate, DayMenu> menuTimeline;

    public MenuTimelineStoreInMemory() {
        menuTimeline = new HashMap<>();
    }

    @Override
    public Optional<DayMenu> menuFor(LocalDate date) {
        DayMenu maybeDayMenu = menuTimeline.get(date);
        return Optional.ofNullable(maybeDayMenu);
    }

    @Override
    public Menu menuFor(LocalDate beginning, LocalDate end) {
        Menu menu = new Menu();
        for (LocalDate currentDate = beginning; currentDate.isBefore(end); currentDate = currentDate.plusDays(1)) {
            DayMenu maybeDayMenu = menuTimeline.get(currentDate);
            if (maybeDayMenu != null) {
                menu.add(maybeDayMenu);
            }
        }
        return menu;
    }

    @Override
    public Optional<LocalDate> firstDate() {
        return menuTimeline.keySet().stream().min(Comparator.naturalOrder());
    }

    @Override
    public Optional<LocalDate> lastDate() {
        return menuTimeline.keySet().stream().max(Comparator.naturalOrder());
    }

    @Override
    public boolean hasMenuFor(LocalDate date) {
        return menuTimeline.containsKey(date);
    }

    @Override
    public boolean overlapsWith(Menu menu) {
        return menu.stream().anyMatch(this::overlapsWith);
    }

    @Override
    public boolean overlapsWith(DayMenu menu) {
        return menuTimeline.containsKey(menu.getDate());
    }

    @Override
    public void acceptMenu(Menu menu, OnAlreadyDefined onAlreadyDefined) {
        if (menu.stream().anyMatch(this::overlapsWith) && onAlreadyDefined.equals(OnAlreadyDefined.FAIL)) {
            throw new IllegalArgumentException("Some of the input menu dates overlap with already defined dates.");
        }
        menu.forEach(dayMenu -> this.acceptMenu(dayMenu, onAlreadyDefined));
    }

    @Override
    public void acceptMenu(DayMenu dayMenu, OnAlreadyDefined onAlreadyDefined) {
        if (overlapsWith(dayMenu)) {
            switch (onAlreadyDefined) {
                case SKIP:
                    return;
                case OVERWRITE:
                    menuTimeline.put(dayMenu.getDate(), dayMenu);
                    return;
                case FAIL:
                    throw new IllegalArgumentException("DayMenu is already specified for date: " + dayMenu.getDate());
                default:
                    throw new IllegalStateException("Unknown value of onAlreadyDefined: " + onAlreadyDefined);
            }
        } else {
            menuTimeline.put(dayMenu.getDate(), dayMenu);
        }
    }

    @Override
    public boolean removeMenu(LocalDate date) {
        return menuTimeline.remove(date) != null;
    }
}
