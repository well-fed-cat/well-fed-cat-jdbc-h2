package xyz.dsemikin.wellfedcat.datamodel.test;

import org.junit.jupiter.api.Test;
import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;

import java.util.List;

public abstract class DishStoreEditableTestBase {

    protected List<Dish> expectedDishes() {
        return ExpectedDishesProvider.getExpectedDishes();
    }

    protected abstract DishStoreEditable getDishStore();

    @Test
    public void test_add_notExists() {
        // TODO
    }

    @Test
    public void test_add_exists() {
        // TODO
    }

    @Test
    public void test_removeByName_exists() {
        // TODO
    }

    @Test
    public void test_removeByName_notExist() {
        // TODO
    }

    @Test
    public void test_removeById_exists() {
        // TODO
    }

    @Test
    public void test_removeById_notExists() {
        // TODO
    }
}
