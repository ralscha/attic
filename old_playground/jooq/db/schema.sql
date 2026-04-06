CREATE DATABASE test;
USE test;

CREATE TABLE address (
  id int auto_increment NOT NULL,
  firstName varchar(255) DEFAULT NULL,
  lastName varchar(255) DEFAULT NULL,
  street varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);
