alter table tasks add column project_id int null;
alter table tasks add foreign key (project_id) references projects(id);