package xyz.dsemikin.wellfedcat.app.dishstore.initializers;

import xyz.dsemikin.wellfedcat.datamodel.Dish;
import xyz.dsemikin.wellfedcat.datamodel.MealTime;
import xyz.dsemikin.wellfedcat.datastore.db.h2.DishStoreDbH2;

import java.util.Collections;
import java.util.HashSet;

public class DbH2RusTranslit {
    public static void main(String[] args) {

        final String dbFilePath = "~/wellfedcat_db";
        final var dishStore = new DishStoreDbH2(dbFilePath);

        dishStore.add(new Dish("ovsyanaja_kasha", "Ovsjanaja kasha", newHashSet(MealTime.BREAKFAST)));
        dishStore.add(new Dish("mannaja_kasha", "Mannaja kasha", newHashSet(MealTime.BREAKFAST)));
        dishStore.add(new Dish("buterbrody", "Buterbrody", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("ovsjanye_hlopja", "Ovsjanyje hlopja/musli", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("blinchiki", "Blinchiki", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("grenki", "Grenki", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("varjonyje_jaica", "Varjonyje jaica", newHashSet(MealTime.BREAKFAST, MealTime.SUPPER)));
        dishStore.add(new Dish("mjagkije_vafli", "Mjagkije vafli", newHashSet(MealTime.BREAKFAST)));
        dishStore.add(new Dish("ryba_s_pesto", "Ryba s pesto i fasolju", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("olivje", "Olivje", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("krabovyj_salat", "Krabovyj salat", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("picca", "Picca", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("na_vynos", "Na vynos", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("guljash", "Guljash", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("ryba_tandury", "Ryba tanduri", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("vok_s_kuricej", "Ovoschi vok s kuricej", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("krasnaja_fasol_s_farshem", "Krasnaja fasol' s farschem", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("zelenaja_fasol_s_bekonom", "Zelenaja faslol' s bekonom", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("tortelini", "Tortellini", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("pelmeni", "Pelmeni", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("pasta_s_moreproduktami", "Pasta s moreproduktami", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("pasta_karbonara", "Pasta karbonara", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("pasta_s_goroshkom_i_bekonom", "Pasta s goroshkom bekonom i slivkami", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("pasta_bolonese", "Pasta bolonjeze s farschem i sousom iz banki", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("kartoshka_s_gribami", "Kartoshka zharennaja s gribami", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("kurica_zharenaja", "Kurica zharennaja s grechej", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("kurica_s_gribami_i_syrom", "Kurica zapechenaja s syrom i gribami", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("farshirovannyje_percy", "Farschirovannyje percy", newHashSet(MealTime.LUNCH)));
        dishStore.add(new Dish("tvorog", "Tvorog", newHashSet(MealTime.SUPPER)));
        dishStore.add(new Dish("jogurt", "Jogurt", newHashSet(MealTime.SUPPER)));
        dishStore.add(new Dish("syrniki", "Syrniki", newHashSet(MealTime.SUPPER)));
        dishStore.add(new Dish("jaichnica", "Jaichnica", newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.add(new Dish("omlet", "Omlet", newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.add(new Dish("sosiski", "Sosiski", newHashSet(MealTime.LUNCH, MealTime.SUPPER)));
        dishStore.add(new Dish("syr_v_duhovke", "Syr v duhovke", newHashSet(MealTime.SUPPER)));
        dishStore.add(new Dish("tvorozhnaja_zapekanka", "Tvorozhnaja zapekanka", newHashSet(MealTime.SUPPER)));
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... objs) {
        HashSet<T> set = new HashSet<>();
        Collections.addAll(set, objs);
        return set;
    }
}
