drop table if exists meals;
drop table if exists user_roles;
drop table if exists users;
drop sequence if exists global_seq;

create sequence global_seq start with 100000;

create table users
(
  id               integer primary key default nextval('global_seq'),
  name             varchar                 not null,
  email            varchar                 not null,
  password         varchar                 not null,
  registered       timestamp default now() not null,
  enabled          bool default true       not null,
  calories_per_day integer default 2000    not null
);
create unique index users_unique_email_idx on users (email);

create table user_roles
(
  user_id integer not null,
  role    varchar,
  constraint user_roles_idx unique (user_id, role),
  foreign key (user_id) references users (id) on delete cascade
);

create table meals
(
    id integer primary key default nextval('global_seq'),
    user_id integer not null,
    datetime timestamp,
    description text,
    calories integer,
    foreign key (user_id) references users (id) on delete cascade
);

create unique index meals_unique_idx on meals (user_id, datetime);