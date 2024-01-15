
insert into company(name) values ('PACKT Publishing');
insert into company(name) values ('Gieman It Solutions');
insert into company(name) values ('Serious WebDev');


insert into project(name, company_id) values ('Enterprise Application Development with Spring and ExtJS', 1);
insert into project(name, company_id) values ('The Spring Framework for Beginners', 1);
insert into project(name, company_id) values ('Advanced Sencha ExtJS4 ', 1);
insert into project(name, company_id) values ('The 3T Project', 2);
insert into project(name, company_id) values ('Breezing', 2);
insert into project(name, company_id) values ('Gieman Website', 2);
insert into project(name, company_id) values ('Internal Office Projects', 3);
insert into project(name, company_id) values ('External Consulting Tasks', 3);


insert into task(project_id, name)values ('1', 'Chapter 1');
insert into task(project_id, name)values ('1', 'Chapter 2');
insert into task(project_id, name)values ('1', 'Chapter 3');

insert into task(project_id, name)values ('2', 'Chapter 1');
insert into task(project_id, name)values ('2', 'Chapter 2');
insert into task(project_id, name)values ('2', 'Chapter 3');

insert into task(project_id, name)values ('3', 'Preface');
insert into task(project_id, name)values ('3', 'Appendix');
insert into task(project_id, name)values ('3', 'Illustrations');

insert into task(project_id, name)values ('4', 'Database Development');
insert into task(project_id, name)values ('4', 'Java development');
insert into task(project_id, name)values ('4', 'Sencha Development');
insert into task(project_id, name)values ('4', 'Testing');

insert into user(user_name, first_name, last_name, email, password, admin_role) values ('jsmith', 'John', 'Smith', 'js@tttracker.com', '$2a$10$ip5EEuSmmmUvvq7bE7THhui6GGP087JYb6vi/vb80jN5TWYOokrVW', 'N');
insert into user(user_name, first_name, last_name, email, password, admin_role) values ('bjones', 'Betty', 'Jones', 'bj@tttracker.com', '$2a$10$NPLX5ij8Bx9OMZRFiWPntOJXen2GPgpdc.XJfwadwhDHmlN0YyjOC','Y');

insert into task_log (task_id, user_id, task_description, task_log_date,task_minutes) values(1,1,'Completed Chapter 1 proof reading',now(), 120);
insert into task_log (task_id, user_id, task_description, task_log_date,task_minutes) values(2,1,'Completed Chapter 2 draft',now(), 240);
insert into task_log (task_id, user_id, task_description, task_log_date,task_minutes) values(3,1,'Completed preparation work for initial draft',now(), 90);
insert into task_log (task_id, user_id, task_description, task_log_date,task_minutes) values(3,1,'Prepared database for Ch3 task',now(), 180);

insert into task_log (task_id, user_id, task_description, task_log_date,task_minutes) values(1,2,'Started Chapter 1 ',now(), 340);
insert into task_log (task_id, user_id, task_description, task_log_date,task_minutes) values(2,2,'Finished Chapter 2 draft',now(), 140);
insert into task_log (task_id, user_id, task_description, task_log_date,task_minutes) values(3,2,'Initial draft work completed',now(), 450);
insert into task_log (task_id, user_id, task_description, task_log_date,task_minutes) values(3,2,'Database design started',now(), 600);
