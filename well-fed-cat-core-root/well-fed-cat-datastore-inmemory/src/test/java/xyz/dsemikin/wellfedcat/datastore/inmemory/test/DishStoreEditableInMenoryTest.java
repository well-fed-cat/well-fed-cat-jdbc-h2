package xyz.dsemikin.wellfedcat.datastore.inmemory.test;

import xyz.dsemikin.wellfedcat.datamodel.DishStoreEditable;
import xyz.dsemikin.wellfedcat.datamodel.test.DishStoreEditableTestBase;

public class DishStoreEditableInMenoryTest extends DishStoreEditableTestBase {

    @Override
    protected DishStoreEditable getDishStore() {
        return InMemoryStoresProvider.dishStore(testDataProvider());
    }

}
