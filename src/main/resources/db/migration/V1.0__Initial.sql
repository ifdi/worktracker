create table if not exists projects
(
    id int not null,
    name varchar (255),
    constraint project_pkey primary key (id)
);

create table if not exists tasks
(
    id bigint not null,
    name varchar (255),
    note varchar (255),
    task_type varchar (255),
    project_id int not null,
    constraint tasks_pkey primary key (id),
    constraint fk_project_id_project foreign key (project_id) references projects(id),
    constraint unique_task_information unique (project_id, name, task_type)
);

create table if not exists users
(
    id bigint not null,
    email varchar (255) not null,
    name varchar (255),
    password varchar(255),
	user_type varchar(15),
	constraint user_pkey primary key (id),
	constraint unique_email unique (email)
);

create table if not exists work
(
    id bigint not null,
    date date,
    hours double precision not null default 0.0,
    task_id bigint not null,
    user_id bigint not null,
    constraint work_pkey primary key (id),
    constraint fk_task_id_tasks foreign key (task_id) references tasks(id),
    constraint fk_user_id_users foreign key (user_id) references users(id)
);
