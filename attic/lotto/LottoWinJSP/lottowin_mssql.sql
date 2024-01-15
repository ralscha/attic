drop table users;
drop table tips;
drop table jokers;
drop table draws;

create table users(userid     varchar(50) not null primary key,
                   name       nvarchar(100) not null,
                   firstname  nvarchar(100) not null,
                   pass       varchar(50) not null,
                   lang      char(4) not null,
                   email      varchar(100) not null,
                   concurrency int null);

create table tips(id        int not null,
                  userid    varchar(50) not null,
                  z1        int not null,
                  z2        int not null,
                  z3        int not null,
                  z4        int not null,
                  z5        int not null,
                  z6        int not null,
                  concurrency int null,
                  primary key(id, userid));

create table jokers(id      int not null,
                    userid    varchar(50) not null,
                    joker   char(6) not null,
                    concurrency int null,
                    primary key(id, userid));

create table draws(number    int not null,
                   drawyear  int not null,
                   drawdate  datetime not null,
                   z1        int not null,
                   z2        int not null,
                   z3        int not null,
                   z4        int not null,
                   z5        int not null,
                   z6        int not null,
                   zz        int not null,
                   joker     char(6),
                   concurrency int null,
                   primary key(number, drawyear));
                   
               

