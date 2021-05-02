-- Intended for H2 DB

-- TODO: Add documentation
create table dish (
    name varchar(50),

    constraint pk_dish primary key (name)
);

create table meal_time (
    name varchar(10),

    constraint pk_meal_time primary key (name)
);

create table dish_meal_time (
    dish_name varchar(50),
    meal_time_name varchar(10),

    constraint pk_dish_meal_time primary key(dish_name, meal_time_name),
    constraint fk_dish_meal_time_dish_name foreign key (dish_name) references dish (name),
    constraint fk_dish_meal_time_meal_time_name foreign key (meal_time_name) references meal_time (name)
);

insert into meal_time (name)
values                ('breakfast');

insert into meal_time (name)
values                ('lunch');

insert into meal_time (name)
values                ('supper');
