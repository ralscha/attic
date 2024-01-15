Ext.define("Changelog.model.User",
{
  extend : "Ext.data.Model",
  uses : [ "Changelog.model.Role" ],
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
  associations : [ {
    type : "hasMany",
    model : "Changelog.model.Role",
    associationKey : "roles",
    foreignKey : "user_id",
    name : "roles"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "userService.read",
      destroy : "userService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});