insert into projects (id, name)
values (1, 'project name 1');

insert into tasks (id, name, note, task_type, project_id)
values (1, 'task name 1', 'note 1', 'RC', '1');

insert into users (id, email, name, password, user_type)
values (1, 'user1@email.com', 'User name 1', 'password1', 'EMPLOYEE');

insert into token (token, user_id)
values ('OQ_2nG-BYHlhQlCC', 1);