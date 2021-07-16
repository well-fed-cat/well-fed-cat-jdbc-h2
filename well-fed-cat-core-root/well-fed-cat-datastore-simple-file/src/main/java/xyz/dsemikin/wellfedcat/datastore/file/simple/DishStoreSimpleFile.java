package xyz.dsemikin.wellfedcat.datastore.file.simple;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishModified;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datastore.inmemory.DishStoreInMemory;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    public Optional<Dish> create(String publicId, String name, Set<MealTime> suitableForMealTimes) {
        // TODO
        throw new RuntimeException("Not Implemented Yet");
    }

    @Override
    public DeleteStatus deleteByName(String name) {
        final DeleteStatus status = storeImpl().deleteByName(name);
        storeObjectsProvider.writeStoreObjectsToFile();
        return status;
    }

    @Override
    public DeleteStatus deleteByPublicId(String publicId) {
        Optional<Dish> maybeDish = getById(publicId);
        if (maybeDish.isPresent()) {
            return deleteByName(maybeDish.get().name());
        } else {
            return DeleteStatus.DOES_NOT_EXIST;
        }
    }

    @Override
    public DeleteStatus deleteByStrongId(UUID strongId) {
        return null;
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
