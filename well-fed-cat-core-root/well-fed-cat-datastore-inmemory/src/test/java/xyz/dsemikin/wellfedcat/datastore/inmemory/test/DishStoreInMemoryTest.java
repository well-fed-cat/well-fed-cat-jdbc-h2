package xyz.dsemikin.wellfedcat.datastore.inmemory.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStore;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.test.DishStoreTestBase;
import xyz.dsemikin.wellfedcat.datastore.inmemory.DishStoreInMemory;

import java.util.List;

public class DishStoreInMemoryTest extends DishStoreTestBase {

    private final static Logger LOGGER = LoggerFactory.getLogger(DishStoreInMemoryTest.class);

    @Override
    protected DishStore getDishStore() {

        final DishStoreEditable dishStore = new DishStoreInMemory();
        List<Dish> expectedDishes = expectedDishes();

        for (var expectedDish : expectedDishes) {
            dishStore.add(expectedDish);
        }
        return dishStore;
    }

    @Test
    public void otherTest() {
        LOGGER.info("We are in the test!!!!!");
        Assertions.assertTrue(true);
    }

}
