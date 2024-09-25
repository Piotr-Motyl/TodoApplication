create table tasks (
    id integer primary key auto_increment,
    description varchar(100) not null,
    task_done boolean,
    task_deadline datetime null
);