Ext.define("Ab.model.ContactGroup",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "name",
    type : "string"
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
      read : "contactGroupService.read",
      create : "contactGroupService.create",
      update : "contactGroupService.update",
      destroy : "contactGroupService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});