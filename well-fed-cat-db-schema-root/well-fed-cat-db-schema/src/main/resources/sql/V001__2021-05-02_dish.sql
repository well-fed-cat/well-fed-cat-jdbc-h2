-- Intended for H2 DB

-- Indices are created explicitly to be able to control their name.
-- This should enable us to change or remove them if needed.


-- Table DISH
--
-- This table (together with DISH_MEAL_TIME and MEAL_TIME)
-- corresponds to `xyz.dsemikin.wellfedcat.datamodel.Dish`
-- and represents dishes (their properties), which can be used
-- to compose them into menus. Their properties are supposed to
-- affect, how the menu is generated.
--
-- In the future additional properties may be added, which can
-- be used for other purposes (e.g. calories or recipes).
--
-- Fields:
--
-- PUBLIC_ID: Unique identifier of the dish (also used as
--            primary key in this table. May be only composed
--            of ASCII letters, digits and underscore (_).
--            This is not enforced by the DB, but by the application,
--            so be careful not to violate this constraint, if
--            editing the DB manually.
--            The idea is that this ID is shorter and simpler, than
--            NAME field (see below), which is more or less arbitrary,
--            e.g. PUBLIC_ID is not allowed to have punctuation and
--            even whitespaces in it. So it should be convenient to use
--            e.g. when using JAVA API of well-fed-cat interactively
--            or when working with DB directly. Otherwise it's functions
--            are similar and mostly mutually replaceable with NAME.
--
-- NAME: Unique identifier of the dish, which is unlike PUBLIC_ID
--       basically does not have restrictions on its content (except
--       length - 100 characters), so it can use e.g. non-latin characters,
--       punctuation signs etc. It is unique, so it can be used to
--       identify particular dish, but it may be relatively complex,
--       thus not very well suitable for use e.g. from command line,
--       thus PUBLIC_ID was introduced. NAME is supposed to be rather
--       used to display in GUI or reports, in generated menus etc.
--       Also at some point full text search in NAME field should be
--       implemented to search for the dishes interactively.
--
-- Cross references:
--
-- MEAL_TIME table over DISH_MEAL_TIME table - specifies for which meal
-- times this dish is suitable. Each MEAL_TIME can be referenced at most
-- once for each DISH. DISH may have none MEAL_TIMES assigned.
-- Can be queried using query similar to this:
--
--    select *
--    from dish
--    left join dish_meal_time on (dish.public_id = dish_meal_time.dish_public_id);
--
create table dish (
    public_id varchar(50) not null,
    name      varchar(100) not null
);

create primary key dish_pkidx_public_id on dish (public_id);
alter table dish add constraint dish_pk_public_id primary key (public_id);

create unique index dish_uidx_name on dish (name);
alter table dish add constraint dish_uq_name unique (name);


-- Table MEAL_TIME
--
-- Represents an enum xyz.dsemikin.wellfedcat.datamodel.MealTime
-- with value "BREAKFAST", "LUNCH" and "SUPPER" to be used in
-- table DISH_MEAL_TIME.
--
create table meal_time (
    name varchar(10) not null
);

create primary key meal_time_pkidx_name on meal_time (name);
alter table meal_time add constraint meal_time_pk_name primary key (name);



-- Table DISH_MEAL_TIME
--
-- This table extends table DISH by providing information about for which
-- time the dish is suitable. See docs of table DISH for more details.
--
create table dish_meal_time (
    dish_public_id varchar(50) not null,
    meal_time_name varchar(10) not null
);

create primary key dish_meal_time_pkidx on dish_meal_time (dish_public_id, meal_time_name);
alter table dish_meal_time add constraint dish_meal_time_pk primary key (dish_public_id, meal_time_name);

create index dish_meal_time_idx_dish_public_id on dish_meal_time (dish_public_id);
alter table dish_meal_time add constraint dish_meal_time_fk_dish_public_id foreign key (dish_public_id) references dish (public_id) on delete cascade on update cascade;

create index dish_meal_time_idx_meal_time_name on dish_meal_time (meal_time_name);
alter table dish_meal_time add constraint dish_meal_time_fk_meal_time_name foreign key (meal_time_name) references meal_time (name) on delete restrict on update cascade;


-- Entries of the MEAL_TIME table - enum values.

-- We capitalize values, because we want to be able to convert them to enum
-- with `MealTime.valueOf()`, which does case-sensitive comparison with
-- enum literal.
insert into meal_time (name)
values                ('BREAKFAST');

insert into meal_time (name)
values                ('LUNCH');

insert into meal_time (name)
values                ('SUPPER');


-- ---------------------------------------------------------------------------
--
-- MENU_TIMELINE
--
-- This table corresponds to object xyz.dsemikin.wellfedcat.datamodel.MenuTimelineStore
-- and represents "day menus" assigned to unique dates. Here "day menu" is
-- set of dishes assigned to meal-times, i.e. breakfast/lunch/supper.
-- Groups of the records from the table represent objects xyz.dsemikin.wellfedcat.datamodel.DayMenu.
--
-- To get names of dishes planned for breakfast on 2021-05-27 one would need to run
-- query:
--
--     select dish.name
--     from menu_timeline
--     inner_join dish on menu_timeline.dish_public_id = dish.public_id
--     where menu_date = DATE'2021-05-27' and meal_time = 'BREAKFAST'
--     order by menu_timeline.dish_position
--
-- Each record defines one dish belonging to particular meal time
-- on particular date. There should be no "day-menus" with the same
-- date and one day-menu cannot have same dish on same meal-time more
-- than once. Thus combination of menu_date, meal_time and dish_public_id
-- is unique within the table (and used a primary key).
--
-- MENU_DATE - date to which dish of this record correspond (day of corresponding
-- day-menu.
--
-- MEAL_TIME - meal-time to which dish of this record belongs within day-menu.
--
-- DISH_PUBLIC_ID - specifies dish of this record. Refers to DISH table.
--
-- DISH_POSITION - ordering of the dishes within the meal-time within the day-menu.
-- No constraints are enforce on this field by the DB and client should not
-- make any assumptions (e.g. it should not assume, that values are different),
-- but when updating the table client should try to maintain proper ordering
-- of dishes using this value.
--

create table menu_timeline (
    menu_date      date        not null,
    meal_time      varchar(10) not null,
    dish_public_id varchar(50) not null,
    dish_position  int         not null
);

create primary key menu_timeline_pkidx on menu_timeline (menu_date, meal_time, dish_public_id);
alter table menu_timeline add constraint menu_timeline_pk primary key (menu_date, meal_time, dish_public_id);

create index menu_timeline_idx_meal_time on menu_timeline (meal_time);
alter table menu_timeline add constraint menu_timeline_fk_meal_time foreign key (meal_time) references meal_time (name);

create index menu_timeline_idx_dish_public_id on menu_timeline (dish_public_id);
alter table menu_timeline add constraint menu_timeline_fk_dish_public_id foreign key (dish_public_id) references dish (public_id);

create index menu_timeline_idx_menu_date on menu_timeline (menu_date); -- we are going to search and filter by date a lot.
