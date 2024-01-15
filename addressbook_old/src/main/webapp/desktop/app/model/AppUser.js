Ext.define("Addressbook.model.AppUser",
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
    name : "passwordNew",
    type : "string"
  }, {
    name : "passwordNewConfirm",
    type : "string"
  }, {
    name : "enabled",
    type : "boolean"
  }, {
    name : "admin",
    type : "boolean"
  }, {
    name : "id",
    type : "int",
    useNull : true
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "appUserService.read",
      create : "appUserService.create",
      update : "appUserService.update",
      destroy : "appUserService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});