
CREATE TABLE WordList (
   hash INTEGER NOT NULL PRIMARY KEY,
   word VARCHAR(100) NOT NULL);

CREATE TABLE Word12 (
   word12Id INTEGER NOT NULL PRIMARY KEY,
   word1    INTEGER NOT NULL,
   word2    INTEGER NOT NULL,
   total    INTEGER NOT NULL);

CREATE UNIQUE INDEX Word12Ix ON Word12(word1, word2)

CREATE TABLE Word3 (
   word3Id INTEGER NOT NULL PRIMARY KEY,
   word1 INTEGER NOT NULL,
   word2 INTEGER NOT NULL,
   word3 INTEGER NOT NULL,
   hits  INTEGER NOT NULL);
   
CREATE UNIQUE INDEX Word3Ix ON Word3(word1, word2, word3)

CREATE TABLE BeginList (
   BeginListId INTEGER NOT NULL PRIMARY KEY,
   word1 INTEGER NOT NULL,
   word2 INTEGER NOT NULL,
   total INTEGER NOT NULL);

CREATE UNIQUE INDEX BeginListIx ON BeginList(word1, word2);