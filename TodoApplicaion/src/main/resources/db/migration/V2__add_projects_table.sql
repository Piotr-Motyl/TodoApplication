create table projects (
    id integer primary key auto_increment,
    description varchar(100) not null,
    project_done boolean,
    project_deadline datetime null
)