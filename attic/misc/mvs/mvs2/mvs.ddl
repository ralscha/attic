drop table BeginList
drop table WordList
drop table Word12
drop table Word3
create table BeginList (id BIGINT NOT NULL AUTO_INCREMENT, word1 INTEGER not null, word2 INTEGER not null, total INTEGER not null, primary key (id))
create table WordList (hash INTEGER not null, word VARCHAR(100) not null, primary key (hash))
create table Word12 (id BIGINT NOT NULL AUTO_INCREMENT, word1 INTEGER not null, word2 INTEGER not null, total INTEGER not null, primary key (id))
create table Word3 (id BIGINT NOT NULL AUTO_INCREMENT, word1 INTEGER not null, word2 INTEGER not null, word3 INTEGER not null, hits INTEGER not null, primary key (id))
