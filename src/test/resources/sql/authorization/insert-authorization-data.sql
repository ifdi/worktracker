insert into users (id, email, name, password, user_type)
values (1, 'user1@email.com', 'User name 1', 'password1', 'EMPLOYEE');
insert into users (id, email, name, password, user_type)
values (2, 'user2@email.com', 'User name 2', 'password2', 'EMPLOYEE');

insert into token (token, user_id)
values ('OQ_2nG-BYHlhQlCC', 1);