# well-fed-cat-core #

The idea is to use this library from groovy or java interactive
interpreter.


## Use with groovy interactively

To use this library with groovy in interactive mode one needs to
use `groovysh` (don't confuse with `groovyConsole`). It allows
to interactively execute groovy statements and use the result
in subsequent statements.

To use this library, corresponding jar file should be in the
classpath.

Example:

Make sure, that `JAVA_HOME` points to JDK16 and it's java is
on the path.

Run

```
/path/to/groovysh -cp /path/to/well-fed-cat.jar
```

Then in groovysh:

```
groovy:000> import dsemikin.wellfedcat.*
===> dsemikin.wellfedcat.*

groovy:000> import java.nio.file.Paths
===> dsemikin.wellfedcat.*, java.nio.file.Paths

groovy:000> dishesStoreFile = Paths.get("C:\\tmp\\WellFedCatDishStore.wds")
===> C:\tmp\WellFedCatDishStore.wds

groovy:000> dishesStore = new DishStoreSimpleFile(dishesStoreFile)
===> DishStoreSimpleFile@1c7350b0

groovy:000> dishStore = dishesStore
===> DishStoreSimpleFile@1c7350b0

groovy:000> dishStore.allDishes()
===> ...
```


## Use with Java interactively

Make sure, that we use JDK 16.

I should admit, that on Windows I like `jshell` more, than
`groovysh`, because it provides tab-completion, which is 
very handy.

```
jshell --class-path path\to\well-fed-cat-lib-interactive-1.0-SNAPSHOT.jar
```

```
|  Welcome to JShell -- Version 16
|  For an introduction type: /help intro

jshell> import dsemikin.wellfedcat.*

jshell> import java.nio.file.Paths

jshell> var dishStoreFile = Paths.get("C:\\tmp\\WellFedCatDishStore.wds")
dishStoreFile ==> C:\tmp\WellFedCatDishStore.wds

jshell> var dishStore = new DishStoreSimpleFile(dishStoreFile)
dishStore ==> DishStoreSimpleFile@726f3b58

jshell> Utils.printDishes(dishStore.allDishes())
Dish[name=Eggs roasted, suitableForMealTimes=[SUPPER, LUNCH]]
Dish[name=Fish with Pesto, suitableForMealTimes=[LUNCH]]
Dish[name=Meat, suitableForMealTimes=[LUNCH]]
...
```
