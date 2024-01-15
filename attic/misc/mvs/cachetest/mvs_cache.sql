CREATE TABLE BeginList (
	word1 int NOT NULL ,
	word2 int NOT NULL ,
	total int NOT NULL 
) 

CREATE TABLE RefNo (
	refno int NOT NULL 
) 

CREATE TABLE Word12 (
	word1 int NOT NULL ,
	word2 int NOT NULL ,
	word3ref int NOT NULL ,
	total int NOT NULL 
) 

CREATE TABLE Word3 (
	ref int NOT NULL ,
	word3 int NOT NULL ,
	hits int NOT NULL 
) 

CREATE TABLE WordList (
	hash int NOT NULL ,
	word varchar (100) NOT NULL 
) 

ALTER TABLE BeginList WITH NOCHECK ADD 
	CONSTRAINT PK_BeginList PRIMARY KEY  CLUSTERED 
	(
		word1,
		word2
	)  

ALTER TABLE RefNo WITH NOCHECK ADD 
	CONSTRAINT PK_RefNo PRIMARY KEY  CLUSTERED 
	(
		refno
	)  

ALTER TABLE Word12 WITH NOCHECK ADD 
	CONSTRAINT PK_Word12 PRIMARY KEY  CLUSTERED 
	(
		word1,
		word2,
		word3ref
	)  

ALTER TABLE Word3 WITH NOCHECK ADD 
	CONSTRAINT PK_Word3 PRIMARY KEY  CLUSTERED 
	(
		ref,
		word3
	)  

ALTER TABLE WordList WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		hash
	)  

