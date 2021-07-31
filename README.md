# well-fed-cat #

## Summary ##

This project is aimed to develop a library and probably end
user application(s) to compose meal plan or menu for the next
week.

It is still work in progress and probably is not very interesting
for end-users.


## TODO ##

* Learn in Gradle how to manage dependencies from different repositories
  and how to create maven artifacts.
* Learn about gradle and java modules.
* Extract "datamodel" module as separate repo (can use subtree)
* Continue work on refactoring of "datamodel" module.
* Extract in-memory implementation into separate repo. Make multi-submodule common.
* Repair in memory implementation
* Complete test-suite for datamodel and data stores and make sure,
  it works for in-memory implementation.
* Extract simple-file implementation as separate repo.
* Repair simple-file implementation and make sure, all tests work.
* Extract SQL-DB implementation into separate repo.
* Repair SQL-DB implementation. Make sure all tests work.
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


## Modularization problem ##

As of now (2021-07-31) well-fed-cat has already multiple submodules. Currently
they are all maintained in the same repository, which makes it not easy to apply
significant changes to the core modules (lots of adjustments need to be done in
the dependent modules, which cannot be done at once).

The solution, which comes to mind is to split the whole system into modules, so
that some modules could be first developed or modified independently and then
other parts of they system could accomodate the changes.

But this unfortunately this approach is not easy to apply cleanly.

The first solution, which came to my mind was:

* We make multiple nested submodule repositories. Each outer one contains those
  modules dependent on it's submodules. E.g. "datamodel" does not have any dependencies
  (by the way it is not true), and datamodel-db-h2 contains then "datamodel" as
  submodule. And then furhter particular module contains datamodel-db-h2 as
  its submodule. This way all "inner" modules can be implemented independently,
  while all outer module have a possibility to pinpoint particular version
  of the inner submodule (and recursively of all nested submodules).

This approach though has multiple weaknesses:

* The structure of the gradle "products" would be not flat, but quite ugly
  nested.
* Much more importantly: the tree of dependencies (even when well formed)
  cannot be represented by nested modules. E.g. two dependencies may depend
  on the same module, so nested module would be repeated. Besides, the current
  module itself could be dependent on the same submodule (should it include
  this submodule again, or use one of its "bigger" dependencies? which one?).

The second problem seems to be fundamental and still not be solved. Basically
"dependency hell" is the manifestation of this problem. And e.g. Maven management
of the transient dependencies is one of they attempts to solve it. In my
opinion it does not really properly solve this problem, but I also don't
know fundamentally different and better solution.

To come around this problem I think we can give up on achieving "generic-flexibility",
i.e. that each module can be maintained independently from others except it's
explicit and trainsient dependencies (but not considering modules, which in turn
depend on it).

Instead we will split modules into two groups (or probably later more):
* core modules:
  * utilities
  * datamodel
  * datamodel-test
  * core (should be renamed to planning-algorithms or something like this)
* store implementations:
  * in-memory store
  * in-file store
  * h2-db-store

Modules from the first group will be combined into single repo.

Modules from the second group will have each it's own repo having "core-modules"
as submodule.

There will be one more repo, which has set of scripts to manage and build all
"specific-implementation" repos, so that one could check, if all are functionall
(not really yet sure, how exactly to acheive this).

There is one further layer of modules: UI. E.g. interactive-console, desktop,
web etc. Theoretically they can be arbitrarily combined with the stores
implementation, which makes the task of managing all this system quite
complicated. Probably we just need to select couple of specific combinations
and just fucuse on those...


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
