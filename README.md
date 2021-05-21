# well-fed-cat #

## Summary ##

This project is aimed to develop a library and probably end
user application(s) to compose meal plan or menu for the next
week.

It is still work in progress and probably is not very interesting
for end-users.


## TODO ##

* Add to the persistence model the way to save menu history, i.e.
  sequence of day-menus for each day in the past. We also would need
  a way to "accept" some menu and so to "commit" a menu into the history.
* During generation of new menu the algorithm should take into account
  the saved history (probably depth can be specified).
* Add flag to dish to exclude it from using in menus.
* Add string tags to dish. Reduce probability of dishes with same tag (if used recently).
* Add api to modify dish (`update()` method, which replaces dish object
  by ID with provided object)
* Add api to modify dish public ID.
* Maybe we need some tools to clean up the history.
* Add automated tests.
* Make DB-based dishStores check schema version.



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
