-- Intended for H2 DB

-- TODO: Add documentation
create table dish (
    name varchar(100),

    constraint pk_dish primary key (name)
);

create table meal_time (
    name varchar(10),

    constraint pk_meal_time primary key (name)
);

create table dish_meal_time (
    dish_name varchar(100),
    meal_time_name varchar(10),

    constraint pk_dish_meal_time primary key(dish_name, meal_time_name),
    constraint fk_dish_meal_time_dish_name foreign key (dish_name) references dish (name) on delete cascade on update cascade,
    constraint fk_dish_meal_time_meal_time_name foreign key (meal_time_name) references meal_time (name) on delete restrict on update cascade
);

-- We capitalize values, because we want to be able to convert them to enum
-- with `MealTime.valueOf()`, which does case-sensitive comparison with
-- enum literal.
insert into meal_time (name)
values                ('BREAKFAST');

insert into meal_time (name)
values                ('LUNCH');

insert into meal_time (name)
values                ('SUPPER');
