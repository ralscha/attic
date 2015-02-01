Ext.define("E4ds.model.User",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "userName",
    type : "string"
  }, {
    name : "name",
    type : "string"
  }, {
    name : "firstName",
    type : "string"
  }, {
    name : "email",
    type : "string"
  }, {
    name : "role",
    type : "string"
  }, {
    name : "passwordNew",
    type : "string"
  }, {
    name : "passwordNewConfirm",
    type : "string"
  }, {
    name : "oldPassword",
    type : "string"
  }, {
    name : "locale",
    type : "string"
  }, {
    name : "enabled",
    type : "boolean"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "userService.read",
      create : "userService.create",
      update : "userService.update",
      destroy : "userService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});