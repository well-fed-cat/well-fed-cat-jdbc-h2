-- Intended for H2 DB

-- TODO: Add documentation
create table dish (
    public_id varchar(50) not null,
    name      varchar(100) not null
);

create primary key dish_pkidx_public_id on dish (public_id);
alter table dish add constraint dish_pk_public_id primary key (public_id);

create unique index dish_uidx_name on dish (name);
alter table dish add constraint dish_uq_name unique (name);



create table meal_time (
    name varchar(10) not null
);

create primary key meal_time_pkidx_name on meal_time (name);
alter table meal_time add constraint meal_time_pk_name primary key (name);



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




-- We capitalize values, because we want to be able to convert them to enum
-- with `MealTime.valueOf()`, which does case-sensitive comparison with
-- enum literal.
insert into meal_time (name)
values                ('BREAKFAST');

insert into meal_time (name)
values                ('LUNCH');

insert into meal_time (name)
values                ('SUPPER');
