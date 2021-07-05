# well-fed-cat #

## Summary ##

This project is aimed to develop a library and probably end
user application(s) to compose meal plan or menu for the next
week.

It is still work in progress and probably is not very interesting
for end-users.


## TODO ##

* Implement automated tests.
  * Implement them for all store-implementations.
  * Create spec of how stores should work together.
  * Make store implementations compliant with the spec.
  * Fix problem with over-writing DayMenus in MenuTimelineStore
* Improve interactive work:
  * For all datamodel classes implement meaningful toString()
  * Implement dishStore.findById("textToSearch")
* During generation of new menu the algorithm should take into account
  the saved history (probably depth can be specified).
* Add "basic probability coefficient" to the dish to make it possible 
  for user to make some dishes more frequent, than another one.
* Add flag to dish to exclude it from using in menus.
* Add flag to disallow dish to be used, more than one time within the week.
* Add string tags to dish. Reduce probability of dishes with same tag (if used recently).
* Add field to define allowed days of week for the dish (mainly for "weekend-dishes").
* Add flag "deleted" to dishes in the DB (do we need to propagate it also
  to the objet, or we should do all the filtering on the "query" stage?),
  so that we can delete some dish from the store, but still show it in the
  menu timeline. Alternatively the flag can be "timeline-only".
* Test MenuTimelineStoreSimpleFile (probably interactively).
* Add api to modify dish (`update()` method, which replaces dish object
  by ID with provided object).
* Add api to modify dish public ID.
* Maybe we need some tools to clean up the history.
* Add automated tests.
* Make DB-based dishStores check schema version.
* Try to use java modules
* Try to properly encapsulate implementation details and restrict visibility.
* Make Javadoc combined from all submodules.



## Controlling probability of dish in the menu ##

* dish has "active" flag. If false, then has probability 0.
* base probability for each dish
* if dish is close in history, it has reducing coef.
* dish has tag. If dish with same tag in history, it has reducing coef.
* dish has "suitable for days of week". On other days its probability is 0.

* would be nice to distribute somehow meat, fish.
* would be nice to add salads
* would be nice to take into account child's preferences.



## Various hints ##

### Setting source encoding in gradle ###

```
compileJava.options.encoding = "UTF-8"
compileTestJava.options.encoding = "UTF-8"
```


### Cyrillic in Windows Terminal ###

On Windows to support output in cyrillic characters (if
default windows code page is "Western" or other non
cyrillic, then use `chcp 866` in the terminal.

When using gradle, to create directory with all jars and
startup script use `gradle installDist` (from distribution
plugin). It will create directory "build\installation"
with needed content.


### Cyrillic in IntelliJ ###

(This did not work for me, probably because default windows code page is not "cyrillic").

If using e.g. with Cyrillic, then IntelliJ by default showns
question marks. To solve it add "-Dfile.encoding=UTF-8" to the
`idea.exe.vmoptions` (in bin dir of IntelliJ, e.g.
`C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.1\bin`)
and to run configurations of the application.

See also [https://intellij-support.jetbrains.com/hc/en-us/community/posts/206971615-Cyrillic-symbols-in-console-on-mac](https://intellij-support.jetbrains.com/hc/en-us/community/posts/206971615-Cyrillic-symbols-in-console-on-mac).


## License and copyright ##

This code is published under GNU AFFERO GENERAL PUBLIC LICENSE
Version 3 (see LICENSE.txt).

Copyright (C) 2021 Dmitrii Semikin <https://dmitrii.semikin.xyz>
