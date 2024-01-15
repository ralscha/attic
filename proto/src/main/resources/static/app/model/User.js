Ext.define("Proto.model.User",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.identifier.Negative", "Ext.data.proxy.Direct", "Ext.data.validator.Email" ],
  identifier : "negative",
  fields : [ {
    name : "loginName",
    type : "string"
  }, {
    name : "lastName",
    type : "string"
  }, {
    name : "firstName",
    type : "string"
  }, {
    name : "email",
    type : "string",
    validators : [ {
      type : "email"
    } ]
  }, {
    name : "role",
    type : "string"
  }, {
    name : "newPassword",
    type : "string"
  }, {
    name : "newPasswordRetype",
    type : "string"
  }, {
    name : "locale",
    type : "string"
  }, {
    name : "enabled",
    type : "boolean"
  }, {
    name : "passwordReset",
    type : "boolean"
  }, {
    name : "failedLogins",
    type : "integer",
    persist : false
  }, {
    name : "lockedOutUntil",
    type : "date",
    dateFormat : "c",
    persist : false
  }, {
    name : "autoOpenView",
    type : "string",
    persist : false
  }, {
    name : "id",
    type : "integer"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "userService.read",
      create : "userService.update",
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