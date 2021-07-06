package xyz.dsemikin.wellfedcat.datastore.file.simple;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishModified;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datastore.inmemory.DishStoreInMemory;

import java.util.List;
import java.util.Optional;

public class DishStoreSimpleFile implements DishStoreEditable {

    // The three objects: StoreObjectsProviderSimpleFile, MenuTimelineStoreSimpleFile and
    // DishStoreSimple file - they are supposed to always exist together. Thus it is
    // OK to have circular dependencies between those three.
    // Alternative would be to have one object managing others (e.g. StoreObjectProviderSimpleFile),
    // but in this case the clint would need to not forget to keep a reference to it
    // while the actual "store objects" are being used.
    private final StoreObjectProviderSimpleFile storeObjectsProvider;

    public DishStoreSimpleFile(final StoreObjectProviderSimpleFile storeObjectsProvider) {
        this.storeObjectsProvider = storeObjectsProvider;
    }

    @Override
    public List<Dish> all() {
        return storeImpl().all();
    }

    @Override
    public Optional<Dish> getByName(String name) {
        return storeImpl().getByName(name);
    }

    @Override
    public Optional<Dish> getById(String publicId) {
        return storeImpl().getById(publicId);
    }

    @Override
    public boolean add(Dish dish) {
        final boolean result = storeImpl().add(dish);
        storeObjectsProvider.writeStoreObjectsToFile();
        return result;
    }

    @Override
    public RemoveStatus removeByName(String name) {
        final RemoveStatus status = storeImpl().removeByName(name);
        storeObjectsProvider.writeStoreObjectsToFile();
        return status;
    }

    @Override
    public RemoveStatus removeById(String publicId) {
        Optional<Dish> maybeDish = getById(publicId);
        if (maybeDish.isPresent()) {
            return removeByName(maybeDish.get().name());
        } else {
            return RemoveStatus.DOES_NOT_EXIST;
        }
    }

    @Override
    public UpdateStatus updateDish(DishModified newDishVersion) {
        // TODO
        throw new RuntimeException("Not implemented yet");
    }

    private DishStoreInMemory storeImpl() {
        return storeObjectsProvider.getDishStoreInMemory();
    }
}
