alter table orderPos drop constraint FK4991C826F15A0051
alter table childMap drop constraint FK62E9D2A0460B8F65
alter table Child drop constraint FK3E104FC460B8F65
drop table orderPos
drop table users
drop table childMap
drop table Parent
drop table Child
drop table orderTotal
create table orderPos (orderPosId VARCHAR(255) not null, description VARCHAR(255) null, no NUMERIC(19,3) null, price NUMERIC(19,3) null, orderTotalId VARCHAR(255) not null, primary key (orderPosId))
create table users (LogonId VARCHAR(255) not null, Name VARCHAR(255) null, Password VARCHAR(255) null, EmailAddress VARCHAR(255) null, LastLogon DATETIME null, primary key (LogonId))
create table childMap (parentId NUMERIC(19,0) not null, descr VARCHAR(100) not null, name VARCHAR(20) not null, primary key (parentId, name))
create table Parent (id NUMERIC(19,0) IDENTITY NOT NULL, primary key (id))
create table Child (id NUMERIC(19,0) IDENTITY NOT NULL, name VARCHAR(20) null, descr VARCHAR(20) null, parentId NUMERIC(19,0) not null, primary key (id))
create table orderTotal (orderId VARCHAR(255) not null, description VARCHAR(255) null, total NUMERIC(19,3) null, primary key (orderId))
alter table orderPos add constraint FK4991C826F15A0051 foreign key (orderTotalId) references orderTotal
alter table childMap add constraint FK62E9D2A0460B8F65 foreign key (parentId) references Parent
alter table Child add constraint FK3E104FC460B8F65 foreign key (parentId) references Parent
