package xyz.dsemikin.wellfedcat.app.dishstore.initializers;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datastore.db.h2.StoreObjectFactoryH2;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;

public class DbH2Rus {
    public static void main(String[] args) throws SQLException {

        final String dbFilePath = "~/wellfedcat_db";
        try (final StoreObjectFactoryH2 storeObjectFactoryH2 = new StoreObjectFactoryH2(dbFilePath)) {
            final var dishStore = storeObjectFactoryH2.createDishStore();

            // TODO: Fix definition of dish.
            dishStore.add(new Dish("ovsyanaja_kasha", "Овсяная каша", newHashSet(MealTime.BREAKFAST)));
            dishStore.add(new Dish("mannaja_kasha", "Манная каша", newHashSet(MealTime.BREAKFAST)));
            dishStore.add(new Dish("buterbrody", "Бутерброды", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
            dishStore.add(new Dish("ovsjanye_hlopja", "Овсяные хлопья/мюсли", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
            dishStore.add(new Dish("blinchiki", "Блинчики", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
            dishStore.add(new Dish("grenki", "Гренки", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
            dishStore.add(new Dish("varjonyje_jaica", "Вареные яйца", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
            dishStore.add(new Dish("mjagkije_vafli", "Мягкие вафли", newHashSet(MealTime.BREAKFAST)));
            dishStore.add(new Dish("ryba_s_pesto", "Рыба с песто и фасолью.", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("olivje", "Оливье", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("krabovyj_salat", "Крабовый салат", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("picca", "Пицца", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("na_vynos", "На вынос", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("guljash", "Гуляш", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("ryba_tandury", "Рыба тандури", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("vok_s_kuricej", "Овощи вок с курицей", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("krasnaja_fasol_s_farshem", "Красная фасоль с фаршем", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("zelenaja_fasol_s_bekonom", "Зеленая фасоль с беконом", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("tortelini", "Тортелини", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("pelmeni", "Пельмени", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("pasta_s_moreproduktami", "Паста с морепродуктами", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("pasta_karbonara", "Паста карбонара", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("pasta_bolonese", "Паста болоньезе (с фаршем и соусом из банки)", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("kartoshka_s_gribami", "Картошка жаренная с грибами", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("kurica_zharenaja", "Курица жаренная", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("kurica_s_gribami_i_syrom", "Курица запеченная с сыром и грибами", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("farshirovannyje_percy", "Фаршированные перцы", newHashSet(MealTime.LUNCH)));
            dishStore.add(new Dish("tvorog", "Творог", newHashSet(MealTime.SUPPER)));
            dishStore.add(new Dish("jogurt", "Йогурт", newHashSet(MealTime.SUPPER)));
            dishStore.add(new Dish("syrniki", "Сырники", newHashSet(MealTime.SUPPER)));
            dishStore.add(new Dish("jaichnica", "Яичница", newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
            dishStore.add(new Dish("omlet", "Омлет", newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
            dishStore.add(new Dish("sosiski", "Сосиски", newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
            dishStore.add(new Dish("syr_v_duhovke", "Сыр в духовке", newHashSet(MealTime.SUPPER)));
            dishStore.add(new Dish("tvorozhnaja_zapekanka", "Творожная запеканка", newHashSet(MealTime.SUPPER)));
        }
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... objs) {
        HashSet<T> set = new HashSet<>();
        Collections.addAll(set, objs);
        return set;
    }
}
