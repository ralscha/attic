Ext.define("Packt.model.security.User",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "name",
    type : "string"
  }, {
    name : "userName",
    type : "string"
  }, {
    name : "email",
    type : "string"
  }, {
    name : "picture",
    type : "string"
  }, {
    name : "appGroupId",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    pageParam : undefined,
    startParam : undefined,
    limitParam : undefined,
    api : {
      read : "userService.readUser",
      create : "userService.create",
      update : "userService.update",
      destroy : "userService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});