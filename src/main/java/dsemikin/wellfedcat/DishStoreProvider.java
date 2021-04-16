package dsemikin.wellfedcat;

public class DishStoreProvider {

    public static DishStore getDishStore() {
        return createAndInitializeInMemoryDishStore();
    }

    private static DishStoreInMemory createAndInitializeInMemoryDishStore() {
        final DishStoreInMemory dishStore = new DishStoreInMemory();
        dishStore.addDish(new Dish("Pasta"));
        dishStore.addDish(new Dish("Pizza"));
        dishStore.addDish(new Dish("Fish with Pesto"));
        dishStore.addDish(new Dish("Kidney Bonen with meat"));
        dishStore.addDish(new Dish("Boiled potato"));
        return dishStore;
    }
}
