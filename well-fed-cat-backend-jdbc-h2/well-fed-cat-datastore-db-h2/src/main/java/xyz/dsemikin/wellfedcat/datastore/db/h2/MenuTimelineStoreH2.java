package xyz.dsemikin.wellfedcat.datastore.db.h2;

import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.Menu;
import xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStoreEditable;

import java.time.LocalDate;
import java.util.Optional;

public class MenuTimelineStoreH2 implements MenuTimelineStoreEditable {

    private MenuTimelineService service;

    /*package*/ MenuTimelineStoreH2(final MenuTimelineService service) {
        this.service = service;
    }

    @Override
    public Optional<DayMenu> menuFor(final LocalDate localDate) {
        return service.menuFor(localDate);
    }

    @Override
    public Menu menuFor(final LocalDate beginning, final LocalDate end) {
        return service.menuFor(beginning, end);
    }

    @Override
    public Optional<LocalDate> firstDate() {
        return service.firstDate();
    }

    @Override
    public Optional<LocalDate> lastDate() {
        return service.lastDate();
    }

    @Override
    public boolean hasMenuFor(final LocalDate date) {
        return service.hasMenuFor(date);
    }

    @Override
    public boolean overlapsWith(final Menu menu) {
        return service.overlapsWith(menu);
    }

    @Override
    public boolean overlapsWith(final DayMenu menu) {
        return service.overlapsWith(menu);
    }

    @Override
    public void acceptMenu(final Menu menu, final OnAlreadyDefined onAlreadyDefined) {
        service.acceptMenu(menu, onAlreadyDefined);
    }

    @Override
    public void acceptMenu(final DayMenu menu, final OnAlreadyDefined onAlreadyDefined) {
        service.acceptMenu(menu, onAlreadyDefined);
    }

    @Override
    public boolean removeMenu(final LocalDate date) {
        return service.removeMenu(date);
    }
}
