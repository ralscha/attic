Ext.define("Packt.model.mail.MailMessage",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "importance",
    type : "boolean"
  }, {
    name : "icon",
    type : "string"
  }, {
    name : "attachment",
    type : "boolean"
  }, {
    name : "from",
    type : "string"
  }, {
    name : "subject",
    type : "string"
  }, {
    name : "received",
    type : "date",
    dateFormat : "c"
  }, {
    name : "flag",
    type : "boolean"
  }, {
    name : "folder",
    type : "string"
  }, {
    name : "id",
    type : "int",
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "mailService.read",
      update : "mailService.update"
    }
  }
});