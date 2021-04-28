package xyz.dsemikin.wellfedcat;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

public class DishStoreProvider {

    public static DishStore getDishStore() {
        final DishStoreInMemory dishStore = new DishStoreInMemory();
        return initializeDishStoreWithSampleData(dishStore);
    }

    public static DishStore getDishStoreSimpleFileAndFillWithSampleData(final Path dishStoreFile) {
        DishStoreSimpleFile dishStore = new DishStoreSimpleFile(dishStoreFile);
        return initializeDishStoreWithSampleData(dishStore);
    }

    /** Returns the same object, which was passed as input argument. */
    private static DishStore initializeDishStoreWithSampleData(final DishStore dishStore) {
        addDishToDishStore(dishStore, "Pasta", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Pizza", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Fish with Pesto", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Kidney beans with meat", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Boiled potato", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Meat", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Fish", MealTime.LUNCH);
        addDishToDishStore(dishStore, "Eggs roasted", MealTime.LUNCH, MealTime.SUPPER);
        addDishToDishStore(dishStore, "Eggs boiled", MealTime.SUPPER, MealTime.BREAKFAST);
        addDishToDishStore(dishStore, "Ovsyanaya kasha", MealTime.BREAKFAST);
        addDishToDishStore(dishStore, "Sandwiches", MealTime.BREAKFAST, MealTime.SUPPER);
        addDishToDishStore(dishStore, "Tvorog", MealTime.SUPPER);
        addDishToDishStore(dishStore, "Musli", MealTime.BREAKFAST, MealTime.SUPPER);
        addDishToDishStore(dishStore, "Yoghurt", MealTime.SUPPER);
        return dishStore;
    }

    private static void addDishToDishStore(
            final DishStore dishStore,
            final String dishName,
            final MealTime... mealTimes
    ) {
        dishStore.addDish(new Dish(dishName, new HashSet<>(Arrays.stream(mealTimes).toList())));
    }
}
