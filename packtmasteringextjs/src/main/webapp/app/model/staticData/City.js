Ext.define("Packt.model.staticData.City",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "city",
    type : "string"
  }, {
    name : "countryId",
    type : "int",
    useNull : true,
    convert : null
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
      read : "cityService.read",
      create : "cityService.create",
      update : "cityService.update",
      destroy : "cityService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});