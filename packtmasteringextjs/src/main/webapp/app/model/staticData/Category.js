Ext.define("Packt.model.staticData.Category",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "name",
    type : "string"
  }, {
    name : "lastUpdate",
    type : "date",
    dateFormat : "c"
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
      read : "categoryService.readCategory",
      create : "categoryService.create",
      update : "categoryService.update",
      destroy : "categoryService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});