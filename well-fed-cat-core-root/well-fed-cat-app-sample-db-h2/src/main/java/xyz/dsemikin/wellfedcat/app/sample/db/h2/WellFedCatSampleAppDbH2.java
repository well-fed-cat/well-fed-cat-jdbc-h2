package xyz.dsemikin.wellfedcat.app.sample.db.h2;

import xyz.dsemikin.wellfedcat.core.Menu;
import xyz.dsemikin.wellfedcat.core.MenuManager;
import xyz.dsemikin.wellfedcat.core.Utils;
import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datastore.db.h2.DishStoreDbH2;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

        dishStore.addDish(new Dish("Бутерброды",                                   newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.addDish(new Dish("Овсяные хлопья/мюсли",                         newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.addDish(new Dish("Блинчики",                                     newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.addDish(new Dish("Гренки",                                       newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.addDish(new Dish("Вареные яйца",                                 newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.addDish(new Dish("Мягкие вафли",                                 newHashSet(MealTime.BREAKFAST)));
        dishStore.addDish(new Dish("Рыба с песто и фасолью.",                      newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Оливье",                                       newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Крабовый салат",                               newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Пицца",                                        newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("На вынос",                                     newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Гуляш",                                        newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Рыба тандури",                                 newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Овощи вок с курицей",                          newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Красная фасоль с фаршем",                      newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Зеленая фасоль с беконом",                     newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Тортелини",                                    newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Пельмени",                                     newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Паста с морепродуктами",                       newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Паста карбонара",                              newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Паста болоньезе (с фаршем и соусом из банки)", newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Картошка жаренная с грибами",                  newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Курица жаренная",                              newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Курица запеченная с сыром и грибами",          newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Фаршированные перцы",                          newHashSet(MealTime.LUNCH)));
        dishStore.addDish(new Dish("Творог",                                       newHashSet(MealTime.SUPPER)));
        dishStore.addDish(new Dish("Йогурт",                                       newHashSet(MealTime.SUPPER)));
        dishStore.addDish(new Dish("Сырники",                                      newHashSet(MealTime.SUPPER)));
        dishStore.addDish(new Dish("Яичница",                                      newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.addDish(new Dish("Омлет",                                        newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.addDish(new Dish("Сосиски",                                      newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.addDish(new Dish("Сыр в духовке",                                newHashSet(MealTime.SUPPER)));
        dishStore.addDish(new Dish("Творожная запеканка",                          newHashSet(MealTime.SUPPER)));


    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... objs) {
        HashSet<T> set = new HashSet<T>();
        Collections.addAll(set, objs);
        return set;
    }
}
