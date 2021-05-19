package xyz.dsemikin.wellfedcat.datastore.inmemory;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.utils.Utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public boolean add(Dish dish) {
        if (dishes.containsKey(dish.name()) || getById(dish.publicId()).isPresent()) {
            return false;
        }
        dishes.put(dish.name(), dish);
        return true;
    }

    @Override
    public RemoveStatus removeByName(String name) {
        Dish removedDish = dishes.remove(name);
        return removedDish == null ? RemoveStatus.DOES_NOT_EXIST : RemoveStatus.SUCCESS;
    }

    @Override
    public RemoveStatus removeById(String publicId) {
        final Optional<Dish> maybeDish = getById(publicId);
        if (maybeDish.isPresent()) {
            return removeByName(maybeDish.get().name());
        } else {
            return RemoveStatus.DOES_NOT_EXIST;
        }
    }
}
