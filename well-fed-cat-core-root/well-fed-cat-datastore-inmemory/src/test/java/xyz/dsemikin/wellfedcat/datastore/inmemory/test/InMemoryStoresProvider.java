package xyz.dsemikin.wellfedcat.datastore.inmemory.test;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.test.TestDataProvider;
import xyz.dsemikin.wellfedcat.datastore.inmemory.DishStoreInMemory;

import java.util.List;

public class InMemoryStoresProvider {

    public static DishStoreEditable dishStore(final TestDataProvider testDataProvider) {

        final DishStoreEditable dishStore = new DishStoreInMemory();
        List<Dish> expectedDishes = testDataProvider.expectedDishes();

        for (var expectedDish : expectedDishes) {
            dishStore.add(expectedDish);
        }
        return dishStore;
    }
}
