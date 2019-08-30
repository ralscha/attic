Ext.define("Starter.model.User",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct", "Ext.data.validator.Email", "Ext.data.validator.Presence" ],
  fields : [ "id", "firstName", {
    name : "lastName",
    validators : [ {
      type : "presence"
    } ]
  }, {
    name : "email",
    validators : [ {
      type : "email"
    } ]
  }, "department" ],
  proxy : {
    type : "direct",
    api : {
      read : "userService.read",
      create : "userService.create",
      update : "userService.update",
      destroy : "userService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});