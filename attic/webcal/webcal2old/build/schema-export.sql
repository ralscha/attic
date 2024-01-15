alter table calEvent drop constraint FKF39FB36CC80AC00D
alter table calEvent drop constraint FKF39FB36CAFCB01C9
alter table calReminderEmails drop constraint FK4646C2D7F668752D
alter table calReminderEmails drop constraint FK4646C2D79E5DDDD7
alter table calRecurrence drop constraint FKD8488D9EADF43D55
alter table calDepartmentConfiguration drop constraint FKC3A14716C80AC00D
alter table calUserEvents drop constraint FKB560E732ADF43D55
alter table calUserEvents drop constraint FKB560E732CE2B2E46
alter table calUser drop constraint FK20A948B9FF7F3DAF
alter table calUserGroupPermissions drop constraint FK48C5EB5E2ECB974A
alter table calUserGroupPermissions drop constraint FK48C5EB5EFF7F3DAF
alter table calEventCategories drop constraint FKB6707EA84D477EB9
alter table calEventCategories drop constraint FKB6707EA8ADF43D55
alter table calAccessDepartmentUsers drop constraint FK9FCB08A4CE2B2E46
alter table calAccessDepartmentUsers drop constraint FK9FCB08A4C80AC00D
alter table calDepartmentResourceGroups drop constraint FKA64F39623756240C
alter table calDepartmentResourceGroups drop constraint FKA64F3962C80AC00D
alter table calReminder drop constraint FK9614F660ADF43D55
alter table calUserResourceGroups drop constraint FK97A4A49B3756240C
alter table calUserResourceGroups drop constraint FK97A4A49BCE2B2E46
alter table calEmail drop constraint FKF39B8C6EC80AC00D
alter table calEmail drop constraint FKF39B8C6ECE2B2E46
alter table calDepartmentUsers drop constraint FK9E988028CE2B2E46
alter table calDepartmentUsers drop constraint FK9E988028C80AC00D
alter table calRecurrenceDay drop constraint FK2319311EEA66186B
alter table calUserConfiguration drop constraint FK36C510BDCE2B2E46
alter table calResource drop constraint FKA0A9F9FC3756240C
alter table calAttachment drop constraint FKF93FCB91ADF43D55
alter table calEventProp drop constraint FKECA634EFADF43D55
drop table calEvent
drop table calConfiguration
drop table calUserGroup
drop table calReminderEmails
drop table calRecurrence
drop table calDepartmentConfiguration
drop table calUserEvents
drop table calResourceGroup
drop table calUser
drop table calPermission
drop table calDepartment
drop table calUserGroupPermissions
drop table calEventCategories
drop table calAccessDepartmentUsers
drop table calDepartmentResourceGroups
drop table calReminder
drop table calUserResourceGroups
drop table calEmail
drop table calDepartmentUsers
drop table calCategory
drop table calRecurrenceDay
drop table calUserConfiguration
drop table calResource
drop table calAttachment
drop table calEventProp
drop table calHoliday
create table calEvent (
   eventId BIGINT IDENTITY NOT NULL,
   version INT not null,
   startDate BIGINT not null,
   endDate BIGINT null,
   allDay BIT not null,
   sensitivity SMALLINT not null,
   importance SMALLINT not null,
   description NVARCHAR(1000) null,
   location NVARCHAR(255) null,
   subject NVARCHAR(255) null,
   uid NVARCHAR(255) null,
   createDate BIGINT null,
   modificationDate BIGINT null,
   calVersion NVARCHAR(5) null,
   sequence INT not null,
   priority INT not null,
   resourceId BIGINT null,
   departmentId BIGINT null,
   primary key (eventId)
)
create table calConfiguration (
   configurationId BIGINT IDENTITY NOT NULL,
   name NVARCHAR(100) not null unique,
   propValue NVARCHAR(1000) null,
   primary key (configurationId)
)
create table calUserGroup (
   userGroupId BIGINT IDENTITY NOT NULL,
   version INT not null,
   name NVARCHAR(255) not null,
   description NVARCHAR(1000) null,
   primary key (userGroupId)
)
create table calReminderEmails (
   emailId BIGINT not null,
   reminderId BIGINT not null,
   primary key (reminderId, emailId)
)
create table calRecurrence (
   recurrenceId BIGINT IDENTITY NOT NULL,
   exclude BIT not null,
   type SMALLINT not null,
   interval INT null,
   dayOfWeekMask INT null,
   dayOfMonth INT null,
   monthOfYear INT null,
   instance INT null,
   occurrences INT null,
   duration INT null,
   always BIT not null,
   until BIGINT null,
   rfcRule NVARCHAR(255) null,
   eventId BIGINT not null,
   primary key (recurrenceId)
)
create table calDepartmentConfiguration (
   departmentConfigurationId BIGINT IDENTITY NOT NULL,
   name NVARCHAR(100) not null,
   propValue NVARCHAR(1000) null,
   departmentId BIGINT not null,
   primary key (departmentConfigurationId)
)
create table calUserEvents (
   eventId BIGINT not null,
   userId BIGINT not null,
   primary key (userId, eventId)
)
create table calResourceGroup (
   resourceGroupId BIGINT IDENTITY NOT NULL,
   version INT not null,
   name NVARCHAR(255) not null,
   description NVARCHAR(1000) null,
   primary key (resourceGroupId)
)
create table calUser (
   userId BIGINT IDENTITY NOT NULL,
   version INT not null,
   userName NVARCHAR(100) not null unique,
   passwordHash NVARCHAR(32) null,
   name NVARCHAR(100) not null,
   firstName NVARCHAR(100) null,
   locale NVARCHAR(10) not null,
   logonToken NVARCHAR(40) null,
   logonTokenTime DATETIME null,
   userGroupId BIGINT null,
   primary key (userId)
)
create table calPermission (
   permissionId BIGINT IDENTITY NOT NULL,
   version INT not null,
   name NVARCHAR(255) not null,
   primary key (permissionId)
)
create table calDepartment (
   departmentId BIGINT IDENTITY NOT NULL,
   version INT not null,
   name NVARCHAR(255) not null,
   description NVARCHAR(1000) null,
   primary key (departmentId)
)
create table calUserGroupPermissions (
   permissionId BIGINT not null,
   userGroupId BIGINT not null,
   primary key (userGroupId, permissionId)
)
create table calEventCategories (
   categoryId BIGINT not null,
   eventId BIGINT not null,
   primary key (eventId, categoryId)
)
create table calAccessDepartmentUsers (
   departmentId BIGINT not null,
   userId BIGINT not null,
   primary key (userId, departmentId)
)
create table calDepartmentResourceGroups (
   departmentId BIGINT not null,
   resourceGroupId BIGINT not null,
   primary key (resourceGroupId, departmentId)
)
create table calReminder (
   reminderId BIGINT IDENTITY NOT NULL,
   minutesBefore INT not null,
   eventId BIGINT not null,
   primary key (reminderId)
)
create table calUserResourceGroups (
   resourceGroupId BIGINT not null,
   userId BIGINT not null,
   primary key (userId, resourceGroupId)
)
create table calEmail (
   emailId BIGINT IDENTITY NOT NULL,
   sequence INT not null,
   email NVARCHAR(100) not null,
   defaultEmail BIT not null,
   userId BIGINT null,
   departmentId BIGINT null,
   primary key (emailId)
)
create table calDepartmentUsers (
   departmentId BIGINT not null,
   userId BIGINT not null,
   primary key (userId, departmentId)
)
create table calCategory (
   categoryId BIGINT IDENTITY NOT NULL,
   version INT not null,
   name NVARCHAR(255) not null,
   description NVARCHAR(1000) null,
   icalKey NVARCHAR(255) not null,
   colour NVARCHAR(6) not null,
   bwColour NVARCHAR(6) not null,
   unknown BIT not null,
   primary key (categoryId)
)
create table calRecurrenceDay (
   recurrenceId BIGINT not null,
   recurrenceDay BIGINT null,
   pos INT not null,
   primary key (recurrenceId, pos)
)
create table calUserConfiguration (
   userConfigurationId BIGINT IDENTITY NOT NULL,
   name NVARCHAR(100) not null,
   propValue NVARCHAR(1000) null,
   userId BIGINT not null,
   primary key (userConfigurationId)
)
create table calResource (
   resourceId BIGINT IDENTITY NOT NULL,
   version INT not null,
   name NVARCHAR(255) not null,
   description NVARCHAR(1000) null,
   resourceGroupId BIGINT not null,
   primary key (resourceId)
)
create table calAttachment (
   attachmentId BIGINT IDENTITY NOT NULL,
   fileName NVARCHAR(255) not null,
   fileSize BIGINT not null,
   contentType NVARCHAR(50) not null,
   description NVARCHAR(1000) null,
   eventId BIGINT not null,
   primary key (attachmentId)
)
create table calEventProp (
   eventId BIGINT not null,
   name NVARCHAR(100) not null,
   propValue NVARCHAR(1000) null
)
create table calHoliday (
   holidayId BIGINT IDENTITY NOT NULL,
   version INT not null,
   name NVARCHAR(255) not null,
   description NVARCHAR(500) null,
   colour NVARCHAR(255) null,
   country NVARCHAR(2) null,
   month INT null,
   dayOfMonth INT null,
   dayOfWeek INT null,
   fromYear INT null,
   toYear INT null,
   builtin BIT not null,
   active BIT not null,
   primary key (holidayId)
)
alter table calEvent add constraint FKF39FB36CC80AC00D foreign key (departmentId) references calDepartment
alter table calEvent add constraint FKF39FB36CAFCB01C9 foreign key (resourceId) references calResource
create index IXF39FB36CADF43D55 on calEvent (eventId)
alter table calReminderEmails add constraint FK4646C2D7F668752D foreign key (reminderId) references calReminder
alter table calReminderEmails add constraint FK4646C2D79E5DDDD7 foreign key (emailId) references calEmail
create index IX4646C2D7F668752D on calReminderEmails (reminderId)
create index IX4646C2D79E5DDDD7 on calReminderEmails (emailId)
alter table calRecurrence add constraint FKD8488D9EADF43D55 foreign key (eventId) references calEvent
create index IXD8488D9EEA66186B on calRecurrence (recurrenceId)
alter table calDepartmentConfiguration add constraint FKC3A14716C80AC00D foreign key (departmentId) references calDepartment
create index IXC3A14716C80AC00D on calDepartmentConfiguration (departmentId)
alter table calUserEvents add constraint FKB560E732ADF43D55 foreign key (eventId) references calEvent
alter table calUserEvents add constraint FKB560E732CE2B2E46 foreign key (userId) references calUser
create index IXB560E732CE2B2E46 on calUserEvents (userId)
create index IXB560E732ADF43D55 on calUserEvents (eventId)
alter table calUser add constraint FK20A948B9FF7F3DAF foreign key (userGroupId) references calUserGroup
create index IX20A948B9CE2B2E46 on calUser (userId)
alter table calUserGroupPermissions add constraint FK48C5EB5E2ECB974A foreign key (permissionId) references calPermission
alter table calUserGroupPermissions add constraint FK48C5EB5EFF7F3DAF foreign key (userGroupId) references calUserGroup
create index IX48C5EB5EFF7F3DAF on calUserGroupPermissions (userGroupId)
create index IX48C5EB5E2ECB974A on calUserGroupPermissions (permissionId)
alter table calEventCategories add constraint FKB6707EA84D477EB9 foreign key (categoryId) references calCategory
alter table calEventCategories add constraint FKB6707EA8ADF43D55 foreign key (eventId) references calEvent
create index IXB6707EA84D477EB9 on calEventCategories (categoryId)
create index IXB6707EA8ADF43D55 on calEventCategories (eventId)
alter table calAccessDepartmentUsers add constraint FK9FCB08A4CE2B2E46 foreign key (userId) references calUser
alter table calAccessDepartmentUsers add constraint FK9FCB08A4C80AC00D foreign key (departmentId) references calDepartment
create index IX9FCB08A4CE2B2E46 on calAccessDepartmentUsers (userId)
create index IX9FCB08A4C80AC00D on calAccessDepartmentUsers (departmentId)
alter table calDepartmentResourceGroups add constraint FKA64F39623756240C foreign key (resourceGroupId) references calResourceGroup
alter table calDepartmentResourceGroups add constraint FKA64F3962C80AC00D foreign key (departmentId) references calDepartment
create index IXA64F3962C80AC00D on calDepartmentResourceGroups (departmentId)
create index IXA64F39623756240C on calDepartmentResourceGroups (resourceGroupId)
alter table calReminder add constraint FK9614F660ADF43D55 foreign key (eventId) references calEvent
create index IX9614F660F668752D on calReminder (reminderId)
alter table calUserResourceGroups add constraint FK97A4A49B3756240C foreign key (resourceGroupId) references calResourceGroup
alter table calUserResourceGroups add constraint FK97A4A49BCE2B2E46 foreign key (userId) references calUser
create index IX97A4A49B3756240C on calUserResourceGroups (resourceGroupId)
create index IX97A4A49BCE2B2E46 on calUserResourceGroups (userId)
alter table calEmail add constraint FKF39B8C6EC80AC00D foreign key (departmentId) references calDepartment
alter table calEmail add constraint FKF39B8C6ECE2B2E46 foreign key (userId) references calUser
alter table calDepartmentUsers add constraint FK9E988028CE2B2E46 foreign key (userId) references calUser
alter table calDepartmentUsers add constraint FK9E988028C80AC00D foreign key (departmentId) references calDepartment
create index IX9E988028C80AC00D on calDepartmentUsers (departmentId)
create index IX9E988028CE2B2E46 on calDepartmentUsers (userId)
alter table calRecurrenceDay add constraint FK2319311EEA66186B foreign key (recurrenceId) references calRecurrence
alter table calUserConfiguration add constraint FK36C510BDCE2B2E46 foreign key (userId) references calUser
create index IX36C510BDCE2B2E46 on calUserConfiguration (userId)
alter table calResource add constraint FKA0A9F9FC3756240C foreign key (resourceGroupId) references calResourceGroup
create index IXA0A9F9FCAFCB01C9 on calResource (resourceId)
alter table calAttachment add constraint FKF93FCB91ADF43D55 foreign key (eventId) references calEvent
create index IXF93FCB91AA85A59E on calAttachment (attachmentId)
alter table calEventProp add constraint FKECA634EFADF43D55 foreign key (eventId) references calEvent
create index IXECA634EFADF43D55 on calEventProp (eventId)
