Ext.define("Starter.model.User",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.identifier.Negative", "Ext.data.proxy.Direct", "Ext.data.validator.Email", "Ext.data.validator.Presence" ],
  identifier : "negative",
  fields : [ "firstName", {
    name : "lastName",
    validators : [ {
      type : "presence"
    } ]
  }, {
    name : "email",
    validators : [ {
      type : "email"
    } ]
  }, "departmentId", "id" ],
  proxy : {
    type : "direct",
    api : {
      read : "userService.read",
      create : "userService.create",
      update : "userService.update",
      destroy : "userService.destroy"
    },
    writer : {
      writeAllFields : true
    }
  }
});