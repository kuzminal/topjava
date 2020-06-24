delete from user_roles;
delete from users;
delete from meals;
alter sequence global_seq restart with 100000;

insert into users (name, email, password) values
  ('user', 'user@yandex.ru', 'password'),
  ('admin', 'admin@gmail.com', 'admin');

insert into user_roles (role, user_id) values
  ('role_user', 100000),
  ('role_admin', 100001);

insert into meals (user_id, datetime, description, calories) values
(100000, '2020-01-30 10:00:00', 'Завтрак', 500),
(100000, '2020-01-30 13:00:00', 'Обед', 1000),
(100000, '2020-01-30 20:00:00', 'Ужин', 500),
(100000, '2020-01-31 00:00:00', 'Еда на граничное значение', 100),
(100000, '2020-01-31 10:00:00', 'Завтрак', 1000),
(100000, '2020-01-31 13:00:00', 'Обед', 500),
(100000, '2020-01-31 20:00:00', 'Ужин', 410);