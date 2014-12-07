Ext.define("Packt.model.security.Group",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "name",
    type : "string"
  }, "permissions", {
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
      read : "groupService.read",
      create : "groupService.create",
      update : "groupService.update",
      destroy : "groupService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});