package xyz.dsemikin.wellfedcat.datastore.file.simple;

import xyz.dsemikin.wellfedcat.datamodel.DayMenu;
import xyz.dsemikin.wellfedcat.datamodel.Menu;
import xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStoreEditable;
import xyz.dsemikin.wellfedcat.datastore.inmemory.MenuTimelineStoreInMemory;

import java.time.LocalDate;
import java.util.Optional;

public class MenuTimelineStoreSimpleFile implements MenuTimelineStoreEditable {

    // The three objects: StoreObjectsProviderSimpleFile, MenuTimelineStoreSimpleFile and
    // DishStoreSimple file - they are supposed to always exist together. Thus it is
    // OK to have circular dependencies between those three.
    // Alternative would be to have one object managing others (e.g. StoreObjectProviderSimpleFile),
    // but in this case the clint would need to not forget to keep a reference to it
    // while the actual "store objects" are being used.
    private final StoreObjectProviderSimpleFile storeObjectsProvider;

    /*package*/ MenuTimelineStoreSimpleFile(StoreObjectProviderSimpleFile storeObjectsProvider) {

        this.storeObjectsProvider = storeObjectsProvider;
    }

    @Override
    public Optional<DayMenu> menuFor(LocalDate localDate) {
        return storeImpl().menuFor(localDate);
    }

    @Override
    public Menu menuFor(LocalDate beginning, LocalDate end) {
        return storeImpl().menuFor(beginning, end);
    }

    @Override
    public Optional<LocalDate> firstDate() {
        return storeImpl().firstDate();
    }

    @Override
    public Optional<LocalDate> lastDate() {
        return storeImpl().lastDate();
    }

    @Override
    public boolean hasMenuFor(LocalDate date) {
        return storeImpl().hasMenuFor(date);
    }

    @Override
    public boolean overlapsWith(Menu menu) {
        return storeImpl().overlapsWith(menu);
    }

    @Override
    public boolean overlapsWith(DayMenu menu) {
        return storeImpl().overlapsWith(menu);
    }


    @Override
    public void acceptMenu(Menu menu, OnAlreadyDefined onAlreadyDefined) {
        storeImpl().acceptMenu(menu, onAlreadyDefined);
        storeObjectsProvider.writeStoreObjectsToFile();
    }

    @Override
    public void acceptMenu(DayMenu menu, OnAlreadyDefined onAlreadyDefined) {
        storeImpl().acceptMenu(menu, onAlreadyDefined);
        storeObjectsProvider.writeStoreObjectsToFile();
    }

    @Override
    public boolean removeMenu(LocalDate date) {
        return false;
    }

    private MenuTimelineStoreInMemory storeImpl() {
        return storeObjectsProvider.getMenuTimelineStoreInMemory();
    }
}
