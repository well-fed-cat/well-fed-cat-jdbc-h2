package xyz.dsemikin.wellfedcat.datamodel;

import java.time.LocalDate;
import java.util.Optional;

/**
 * This interface represents mapping of DayMenus to particular dates, which
 * may be considered as both: menu history (if dates are in the past) or
 * menu plan (if dates are in the future).
 *
 * The word "store" in the name should mimic the intent, that implementation
 * of this interface provides some way of persistence (e.g. file or DB), but
 * declared methods do not really imply it.
 */
public interface MenuTimelineStore {

    // TODO: Don't return Optional<> - DayMenu itself can be empty
    /** Get menu for specified date. Returns `empty` if date does not have menu assigned. */
    Optional<DayMenu> menuFor(final LocalDate localDate);

    /** Get menu for given time range. Dates, which do not have menu assigned will not be included. */
    Menu menuFor(final LocalDate beginning, final LocalDate end);

    /** "Earliest" date, which contains menu. `Optional.empty()` if this object does not have any Menus in it. */
    Optional<LocalDate> firstDate();

    /** "Latest" date, which contains menu. `Optional.empty()` if this object does not have any Menus in it. */
    Optional<LocalDate> lastDate();

    boolean hasMenuFor(final LocalDate date);

    boolean overlapsWith(final Menu menu);

    boolean overlapsWith(final DayMenu menu);
}
