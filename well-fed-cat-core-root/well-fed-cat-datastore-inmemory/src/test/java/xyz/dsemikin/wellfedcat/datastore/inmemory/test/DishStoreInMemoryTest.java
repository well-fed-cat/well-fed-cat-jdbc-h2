package xyz.dsemikin.wellfedcat.datastore.inmemory.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dsemikin.wellfedcat.datamodel.DishStore;
import xyz.dsemikin.wellfedcat.datamodel.test.DishStoreTestBase;

public class DishStoreInMemoryTest extends DishStoreTestBase {

    @SuppressWarnings("unused")
    private final static Logger LOGGER = LoggerFactory.getLogger(DishStoreInMemoryTest.class);

    @Override
    protected DishStore getDishStore() {
        return InMemoryStoresProvider.dishStore(testDataProvider());
    }

}
