CREATE TABLE BeginList (
	word1 int NOT NULL ,
	word2 int NOT NULL ,
	total int NOT NULL, 
    CONSTRAINT PK_BeginList PRIMARY KEY(
        word1,word2
    ))
    ENGINE=InnoDB;  

CREATE TABLE RefNo (
	refno int NOT NULL, 
    CONSTRAINT PK_RefNo PRIMARY KEY(
        refno
    ))
    ENGINE=InnoDB;  

CREATE TABLE Word12 (
	word1 int NOT NULL ,
	word2 int NOT NULL ,
	word3ref int NOT NULL ,
	total int NOT NULL, 
    CONSTRAINT PK_Word12 PRIMARY KEY(
        word1,word2,word3ref
    ))
    ENGINE=InnoDB;  

CREATE TABLE Word3 (
	ref int NOT NULL ,
	word3 int NOT NULL ,
	hits int NOT NULL, 
    CONSTRAINT PK_Word3 PRIMARY KEY(
        ref,word3
    ))
    ENGINE=InnoDB;  

CREATE TABLE WordList (
	hash int NOT NULL ,
	word varchar (100) NOT NULL, 
    CONSTRAINT PK_WordList PRIMARY KEY(
        hash
    ))
    ENGINE=InnoDB;  
