package xyz.dsemikin.wellfedcat.datastore.inmemory;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishModified;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.utils.Utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DishStoreInMemory
        implements DishStoreEditable, Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    // TODO: use publicId as key. It would be more logical, even though functionally it is exactly the same
    private final Map<String, Dish> dishes;

    public DishStoreInMemory() {
        dishes = new HashMap<>();
    }

    @Override
    public List<Dish> all() {
        return new ArrayList<>(dishes.values());
    }

    @Override
    public Optional<Dish> getByName(String name) {
        return Optional.ofNullable(dishes.get(name));
    }

    @Override
    public Optional<Dish> getById(String publicId) {
        List<Dish> foundDishes = dishes.values().stream()
                .filter(d -> d.publicId().equals(publicId))
                .collect(Collectors.toList());
        Utils.assertState(foundDishes.size() <= 1);
        return foundDishes.isEmpty() ? Optional.empty() : Optional.of(foundDishes.get(0));
    }

    @Override
    public Optional<Dish> create(String publicId, String name, Set<MealTime> suitableForMealTimes) {
        // TODO
        throw new RuntimeException("Not Implemented Yet");
    }

    @Override
    public DeleteStatus deleteByName(String name) {
        Dish removedDish = dishes.remove(name);
        return removedDish == null ? DeleteStatus.DOES_NOT_EXIST : DeleteStatus.SUCCESS;
    }

    @Override
    public DeleteStatus deleteByPublicId(String publicId) {
        final Optional<Dish> maybeDish = getById(publicId);
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
}
