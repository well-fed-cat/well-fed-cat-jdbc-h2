package dsemikin.wellfedcat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DishPicker {

    private static final int DISH_HISTORY_DEPTH = 30;

    private final List<Dish> allDishes;
    private final List<Integer> dishHistory; // Contains indices from `allDishes`

    public DishPicker() {
        final DishStore dishStore = DishStoreProvider.getDishStore();
        allDishes = dishStore.allDishes();
        dishHistory = new LinkedList<>(); // TODO: It should be possible to restore it from some storage
    }

    public Dish pickNextDish() {
        return allDishes.get(pickNextDishIdx());
    }

    public Dish pickNextDishAndUpdateHistory() {
        int pickedDishIdx = pickNextDishIdx();
        final Dish pickedDish = allDishes.get(pickedDishIdx);
        updateDishesHistory(pickedDishIdx);
        return pickedDish;
    }

    private int pickNextDishIdx() {
        final List<Double> probabilityWeightsOfDishes = generateProbabilityWeightsOfDishes();
        return ProbabilityObjectPicker.pick(probabilityWeightsOfDishes);
    }

    private List<Double> generateProbabilityWeightsOfDishes() {
        //noinspection Convert2Diamond
        var probabilityWeights = new ArrayList<Double>(Collections.nCopies(allDishes.size(), 1.0D));
        for (int kk = 0; kk < dishHistory.size(); kk++) {
            final int dishIdx = dishHistory.get(kk);
            final double epsilon = 0.01; // we don't want, that latest dish has 0 probability.
            final double probabilityWeightMultiplier = 1.0D - 1.0D/(dishHistory.size() - kk + epsilon);
            final double currentProbabilityWeight = probabilityWeights.get(dishIdx);
            final double newProbabilityWeight = probabilityWeightMultiplier * currentProbabilityWeight;
            probabilityWeights.set(dishIdx, newProbabilityWeight);
        }
        return probabilityWeights;
    }

    public void updateDishesHistory(final int latestDishIdx) {
        dishHistory.add(latestDishIdx);
        if (dishHistory.size() >= DISH_HISTORY_DEPTH) {
            dishHistory.remove(0);
        }
        if (dishHistory.size() >= DISH_HISTORY_DEPTH) {
            throw new IllegalStateException("Internal Error. Dish history size must not exceed DISH_HISTORY_DEPTH.");
        }
    }
}
