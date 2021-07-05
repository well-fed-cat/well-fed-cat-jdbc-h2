package xyz.dsemikin.wellfedcat.datastore.db.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datamodel.Menu;
import xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.StoreException;
import xyz.dsemikin.wellfedcat.datamodel.implementation.DayMenuSkeleton;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * Current implementation uses as a main goal to keep SQL queries
 * in DAO objects as simple as possible. For this we take out logic
 * from the queries and implement it in Java in this class. This
 * should make it possible to use as few DB-specific features as
 * possible.
 *
 * Note, that this may have significant performance penalty. Mainly
 * because number of requests to the DB is quite high.
 *
 * If poor performance will be an issue, it should be considered to
 * rewrite DAO and this class to move logic into the queries (probably
 * sacrificing DB-independence), which may dramatically reduce number
 * of requests to DB and thus is expected to significantly increase
 * the performance.
 */
@SuppressWarnings("ClassCanBeRecord")
public class MenuTimelineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuTimelineService.class);

    private final MenuTimelineDao menuTimelineDao;
    private final DishDao dishDao;

    public MenuTimelineService(
            final MenuTimelineDao menuTimelineDao,
            final DishDao dishDao
    ) {
        this.menuTimelineDao = menuTimelineDao;
        this.dishDao = dishDao;
    }

    /** It will fail, if menu uses dishes, which are not in DishStore */
    public void acceptMenu(final DayMenu menu, final MenuTimelineStoreEditable.OnAlreadyDefined onAlreadyDefined) {
        menuTimelineDao.acceptMenu(menu, onAlreadyDefined);
    }

    /** It will fail, if menu uses dishes, which are not in DishStore */
    public void acceptMenu(
            final Menu menu,
            final MenuTimelineStoreEditable.OnAlreadyDefined onAlreadyDefined
    ) {
        final List<LocalDate> dates = menu.stream().map(DayMenu::getDate).collect(Collectors.toList());
        final List<LocalDate> daysWithMenuDefined = dates.stream().filter(this::hasMenuFor).collect(Collectors.toList());
        if (!daysWithMenuDefined.isEmpty()) {
            if (onAlreadyDefined.equals(MenuTimelineStoreEditable.OnAlreadyDefined.SKIP)) {
                return;
            } else if (onAlreadyDefined.equals(MenuTimelineStoreEditable.OnAlreadyDefined.FAIL)) {
                final StringBuilder message = new StringBuilder();
                message.append("There are dates, for which menu is already defined: " );
                for (var day : daysWithMenuDefined) {
                    message.append(day).append(", ");
                }
                message.delete(message.length() - 2, message.length());
                throw new StoreException(message.toString());
            }
        }

        // TODO: probably we could create helper function "withManualCommitAtTheEnd()", which would do handling of all the exceptions etc.
        final boolean autoCommitOld;
        try {
            autoCommitOld = menuTimelineDao.getAutoCommit();
        } catch (Exception e) {
            throw new StoreException("Failed to get current AutoCommit value.", e);
        }

        try {
            menuTimelineDao.setAutoCommit(false);

            for (var day : daysWithMenuDefined) {
                menuTimelineDao.removeMenu(day);
            }

            for (var dayMenu : menu) {
                menuTimelineDao.acceptMenu(dayMenu, onAlreadyDefined);
            }

            menuTimelineDao.commit();

        } catch (Exception e) {
            try {
                menuTimelineDao.rollback();
            } catch (Exception rollbackException) {
                LOGGER.error("Failed to rollback current transaction.", rollbackException);
            }
        } finally {
            try {
                menuTimelineDao.setAutoCommit(autoCommitOld);
            } catch (Exception setAutoCommitException) {
                LOGGER.error("Failed to restore autocommit value.", setAutoCommitException);
            }
        }
    }

    public boolean hasMenuFor(final LocalDate date) {
        return menuTimelineDao.hasMenuFor(date);
    }

    public Optional<DayMenu> menuFor(final LocalDate date) {
        DayMenuSkeleton dayMenuSkeleton = menuTimelineDao.menuFor(date);
        return inflate(dayMenuSkeleton);
    }

    /** "end" is not included. If "end" is before "beginning", empty menu is returned. */
    public Menu menuFor(final LocalDate beginning, final LocalDate end) {
        Menu menu = new Menu();
        for (LocalDate day = beginning; day.isBefore(end); day = day.plusDays(1)) {
            var maybeDayMenu = menuFor(day);
            maybeDayMenu.ifPresent(menu::add);
        }
        return menu;
    }

    public Optional<DayMenu> inflate(final DayMenuSkeleton menuSkeleton) {
        if (menuSkeleton.isEmpty()) {
            return Optional.empty();
        }
        final DayMenu dayMenu = new DayMenu(menuSkeleton.date());
        for (var mealTime : MealTime.values()) {
            for (var dishId : menuSkeleton.get(mealTime)) {
                Optional<Dish> maybeDish = dishDao.getById(dishId);
                maybeDish.ifPresent(dish -> dayMenu.get(mealTime).add(dish));
            }
        }
        return Optional.of(dayMenu);
    }

    public Optional<LocalDate> firstDate() {
        return menuTimelineDao.firstDate();
    }

    public Optional<LocalDate> lastDate() {
        return menuTimelineDao.lastDate();
    }

    public boolean overlapsWith(final Menu menu) {
        return menu.stream().anyMatch(this::overlapsWith);
    }

    public boolean overlapsWith(final DayMenu menu) {
        return hasMenuFor(menu.getDate());
    }

    public boolean removeMenu(final LocalDate date) {
        return menuTimelineDao.removeMenu(date);
    }
}
