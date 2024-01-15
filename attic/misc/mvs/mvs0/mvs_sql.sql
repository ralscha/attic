CREATE TABLE [dbo].[BeginList] (
	[word1] [int] NOT NULL ,
	[word2] [int] NOT NULL ,
	[total] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[RefNo] (
	[refno] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[Word12] (
	[word1] [int] NOT NULL ,
	[word2] [int] NOT NULL ,
	[word3ref] [int] NOT NULL ,
	[total] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[Word3] (
	[ref] [int] NOT NULL ,
	[word3] [int] NOT NULL ,
	[hits] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[WordList] (
	[hash] [int] NOT NULL ,
	[word] [varchar] (100) COLLATE Latin1_General_CI_AS NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[BeginList] WITH NOCHECK ADD 
	CONSTRAINT [PK_BeginList] PRIMARY KEY  CLUSTERED 
	(
		[word1],
		[word2]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[RefNo] WITH NOCHECK ADD 
	CONSTRAINT [PK_RefNo] PRIMARY KEY  CLUSTERED 
	(
		[refno]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[Word12] WITH NOCHECK ADD 
	CONSTRAINT [PK_Word12] PRIMARY KEY  CLUSTERED 
	(
		[word1],
		[word2],
		[word3ref]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[Word3] WITH NOCHECK ADD 
	CONSTRAINT [PK_Word3] PRIMARY KEY  CLUSTERED 
	(
		[ref],
		[word3]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[WordList] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[hash]
	)  ON [PRIMARY] 
GO

