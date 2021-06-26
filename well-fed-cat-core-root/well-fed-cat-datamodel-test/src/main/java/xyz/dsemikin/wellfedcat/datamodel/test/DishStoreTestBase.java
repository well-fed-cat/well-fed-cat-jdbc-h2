package xyz.dsemikin.wellfedcat.datamodel.test;

import org.junit.jupiter.api.Test;
import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static xyz.dsemikin.wellfedcat.datamodel.MealTime.BREAKFAST;
import static xyz.dsemikin.wellfedcat.datamodel.MealTime.LUNCH;
import static xyz.dsemikin.wellfedcat.datamodel.MealTime.SUPPER;

/**
 * This class provides implementation of tests for checking
 * implementations of DishStore interface. Specific implementations
 * of the tests (provided for specific implementations of the
 * interface) should close some missing gaps in test fixtures,
 * like filling in the store. Content for this is provided by
 * this class in protected methods.
 */
public abstract class DishStoreTestBase {

    /**
     * This method returns list of Dish objects expected to be
     * stored in the DishStore for the tests (and ONLY these
     * Dishes).
     *
     * Implementations of this class must use this method to
     * fill the store.
     */
    protected List<Dish> expectedDishes() {
        final List<Dish> expectedDishes = new ArrayList<>();

        expectedDishes.add(Dish.make("boiled_eggs", "Boiled Eggs", BREAKFAST, SUPPER));
        expectedDishes.add(Dish.make("sandwich", "Sandwich", BREAKFAST, SUPPER));
        expectedDishes.add(Dish.make("granola", "Granola", BREAKFAST));
        expectedDishes.add(Dish.make("omelette", "Omelette", SUPPER));
        expectedDishes.add(Dish.make("yoghurt", "Yoghurt", BREAKFAST));
        expectedDishes.add(Dish.make("oatmeal", "Oatmeal", SUPPER));
        expectedDishes.add(Dish.make("steak", "Steak", LUNCH));
        expectedDishes.add(Dish.make("pasta_carbonara", "Pasta Carbonara", LUNCH, SUPPER));
        expectedDishes.add(Dish.make("pizza", "Pizza", BREAKFAST, LUNCH, SUPPER));

        return expectedDishes;
    }

    /**
     * Implement this in the specific test implementations to provide DishStore object under the test.
     */
    protected abstract DishStore getDishStore();

    @Test
    public void test_all() {
        final DishStore dishStore = getDishStore();
        List<Dish> allDishes = dishStore.all();
        Set<Dish> actualDishes = new HashSet<>(allDishes);
        Set<Dish> expectedDishes = new HashSet<>(expectedDishes());
        assertEquals(expectedDishes, actualDishes);
    }

    @Test
    public void test_getByName_exists() {
        final String dishName = "Pasta Carbonara";
        final DishStore dishStore = getDishStore();
        Optional<Dish> maybeActualDish = dishStore.getByName(dishName);
        assertTrue(maybeActualDish.isPresent());
        final Dish actualDish = maybeActualDish.get();

        //noinspection OptionalGetWithoutIsPresent
        final Dish expectedDish = expectedDishes().stream()
                .filter(dish -> dish.name().equals(dishName))
                .findFirst().get();

        assertEquals(expectedDish, actualDish);
    }

    @Test
    public void test_getByName_notExist() {
        final String dishName = "Pasta Bolognese";
        final DishStore dishStore = getDishStore();
        Optional<Dish> maybeActualDish = dishStore.getByName(dishName);
        assertTrue(maybeActualDish.isEmpty());
    }

    @Test
    public void test_getById_exists() {
        final String dishId = "granola";
        final DishStore dishStore = getDishStore();
        Optional<Dish> maybeActualDish = dishStore.getById(dishId);
        assertTrue(maybeActualDish.isPresent());
        final Dish actualDish = maybeActualDish.get();

        //noinspection OptionalGetWithoutIsPresent
        final Dish expectedDish = expectedDishes().stream()
                .filter(dish -> dish.publicId().equals(dishId))
                .findFirst().get();

        assertEquals(expectedDish, actualDish);
    }

    @Test
    public void test_getById_notExist() {
        final String dishId = "pelmeni";
        final DishStore dishStore = getDishStore();
        Optional<Dish> maybeActualDish = dishStore.getById(dishId);
        assertTrue(maybeActualDish.isEmpty());
    }
}
