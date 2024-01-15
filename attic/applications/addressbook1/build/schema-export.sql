alter table ContactAttribute drop constraint FKC83D8E7CE77EA55B
drop table ContactAttribute
drop table Contact
create table ContactAttribute (
   contactId NUMERIC(19,0) not null,
   value VARCHAR(500) not null,
   field VARCHAR(255) not null,
   primary key (contactId, field)
)
create table Contact (
   id NUMERIC(19,0) not null,
   version INT not null,
   category VARCHAR(10) not null,
   primary key (id)
)
alter table ContactAttribute add constraint FKC83D8E7CE77EA55B foreign key (contactId) references Contact
