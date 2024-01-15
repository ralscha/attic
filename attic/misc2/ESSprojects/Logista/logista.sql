/* Microsoft SQL Server - Scripting			*/
/* Server: ENTE					*/
/* Database: Logista					*/
/* Creation Date 4/3/00 11:33:51 AM 			*/

/****** Object:  Table dbo.Benutzer    Script Date: 4/3/00 11:33:52 AM ******/
if exists (select * from sysobjects where id = object_id('dbo.Benutzer') and sysstat & 0xf = 3)
	drop table dbo.Benutzer
GO

/****** Object:  Table dbo.MaschinenPool    Script Date: 4/3/00 11:33:52 AM ******/
if exists (select * from sysobjects where id = object_id('dbo.MaschinenPool') and sysstat & 0xf = 3)
	drop table dbo.MaschinenPool
GO

/****** Object:  Table dbo.MaschinenRubrik    Script Date: 4/3/00 11:33:52 AM ******/
if exists (select * from sysobjects where id = object_id('dbo.MaschinenRubrik') and sysstat & 0xf = 3)
	drop table dbo.MaschinenRubrik
GO

/****** Object:  Table dbo.MitarbeiterPool    Script Date: 4/3/00 11:33:52 AM ******/
if exists (select * from sysobjects where id = object_id('dbo.MitarbeiterPool') and sysstat & 0xf = 3)
	drop table dbo.MitarbeiterPool
GO

/****** Object:  Table dbo.MitarbeiterRubrik    Script Date: 4/3/00 11:33:52 AM ******/
if exists (select * from sysobjects where id = object_id('dbo.MitarbeiterRubrik') and sysstat & 0xf = 3)
	drop table dbo.MitarbeiterRubrik
GO

/****** Object:  Table dbo.Benutzer    Script Date: 4/3/00 11:33:52 AM ******/
CREATE TABLE dbo.Benutzer (
	BenutzerID smallint NOT NULL ,
	Name varchar (50) NULL ,
	Passwort varchar (30) NULL ,
	Profil char (1) NULL 
)
GO

setuser 'dbo'
GO

EXEC sp_bindefault 'dbo.UW_ZeroDefault', 'Benutzer.BenutzerID'
GO

setuser
GO

/****** Object:  Table dbo.MaschinenPool    Script Date: 4/3/00 11:33:52 AM ******/
CREATE TABLE dbo.MaschinenPool (
	ID int IDENTITY (1, 1) NOT NULL ,
	Name varchar (50) NULL ,
	Email varchar (50) NULL ,
	Telefon varchar (50) NULL ,
	Passwort varchar (50) NULL ,
	Titel varchar (50) NULL ,
	Beschreibung varchar (200) NULL ,
	Gueltigbis varchar (50) NULL ,
	MaschinenRubrik smallint NULL ,
	Loeschennach int NULL ,
	DatumErstellung datetime NULL 
)
GO

setuser 'dbo'
GO

EXEC sp_bindefault 'dbo.UW_ZeroDefault', 'MaschinenPool.Loeschennach'
GO

EXEC sp_bindefault 'dbo.UW_ZeroDefault', 'MaschinenPool.MaschinenRubrik'
GO

setuser
GO

/****** Object:  Table dbo.MaschinenRubrik    Script Date: 4/3/00 11:33:52 AM ******/
CREATE TABLE dbo.MaschinenRubrik (
	Beschreibung varchar (100) NULL ,
	ID int IDENTITY (1, 1) NOT NULL 
)
GO

 CREATE  INDEX ID2 ON dbo.MaschinenRubrik(ID)
GO

/****** Object:  Table dbo.MitarbeiterPool    Script Date: 4/3/00 11:33:53 AM ******/
CREATE TABLE dbo.MitarbeiterPool (
	ID int IDENTITY (1, 1) NOT NULL ,
	SucheBiete char (1) NULL ,
	Name varchar (50) NULL ,
	Email varchar (50) NULL ,
	Telefon varchar (30) NULL ,
	Passwort varchar (30) NULL ,
	Titel varchar (100) NULL ,
	Beschreibung varchar (200) NULL ,
	Gueltigbis varchar (50) NULL ,
	MitarbeiterRubrik smallint NULL ,
	Loeschennach int NULL ,
	DatumErstellung datetime NULL 
)
GO

 CREATE  INDEX ID ON dbo.MitarbeiterPool(ID)
GO

setuser 'dbo'
GO

EXEC sp_bindefault 'dbo.UW_ZeroDefault', 'MitarbeiterPool.Loeschennach'
GO

EXEC sp_bindefault 'dbo.UW_ZeroDefault', 'MitarbeiterPool.MitarbeiterRubrik'
GO

setuser
GO

/****** Object:  Table dbo.MitarbeiterRubrik    Script Date: 4/3/00 11:33:53 AM ******/
CREATE TABLE dbo.MitarbeiterRubrik (
	Beschreibung varchar (100) NULL ,
	ID int IDENTITY (1, 1) NOT NULL 
)
GO

 CREATE  INDEX ID2 ON dbo.MitarbeiterRubrik(ID)
GO

