package xyz.dsemikin.wellfedcat.app.sample.db.h2;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.DishStoreException;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datastore.db.h2.DishStoreDbH2;

import java.util.Collections;
import java.util.HashSet;

public class WellFedCatSampleAppDbH2 {
    public static void main(String[] args) {
//        final String dbFilePath = "~/wellfedcat_db";
//        final var dishStore = new DishStoreDbH2(dbFilePath);
//        final var menuManager = new MenuManager(dishStore);
//        final Menu menu = menuManager.generateWeekMenu(LocalDate.now());
//        Utils.printMenu(menu);


        final String dbFilePath = "~/wellfedcat_db";
        final var dishStore = new DishStoreDbH2(dbFilePath);

//        dishStore.addDish(new Dish("Овсяная каша", newHashSet(MealTime.BREAKFAST)));
//        dishStore.addDish(new Dish("Манная каша", newHashSet(MealTime.BREAKFAST)));

        dishStore.add(new Dish("Бутерброды",                                   newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("Овсяные хлопья/мюсли",                         newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("Блинчики",                                     newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("Гренки",                                       newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("Вареные яйца",                                 newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("Мягкие вафли",                                 newHashSet(MealTime.BREAKFAST)));
        dishStore.add(new Dish("Рыба с песто и фасолью.",                      newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Оливье",                                       newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Крабовый салат",                               newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Пицца",                                        newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("На вынос",                                     newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Гуляш",                                        newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Рыба тандури",                                 newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Овощи вок с курицей",                          newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Красная фасоль с фаршем",                      newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Зеленая фасоль с беконом",                     newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Тортелини",                                    newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Пельмени",                                     newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Паста с морепродуктами",                       newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Паста карбонара",                              newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Паста болоньезе (с фаршем и соусом из банки)", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Картошка жаренная с грибами",                  newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Курица жаренная",                              newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Курица запеченная с сыром и грибами",          newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Фаршированные перцы",                          newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("Творог",                                       newHashSet(MealTime.SUPPER)));
        dishStore.add(new Dish("Йогурт",                                       newHashSet(MealTime.SUPPER)));
        dishStore.add(new Dish("Сырники",                                      newHashSet(MealTime.SUPPER)));
        dishStore.add(new Dish("Яичница",                                      newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.add(new Dish("Омлет",                                        newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.add(new Dish("Сосиски",                                      newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.add(new Dish("Сыр в духовке",                                newHashSet(MealTime.SUPPER)));
        dishStore.add(new Dish("Творожная запеканка",                          newHashSet(MealTime.SUPPER)));


    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... objs) {
        HashSet<T> set = new HashSet<>();
        Collections.addAll(set, objs);
        return set;
    }
}
