Ext.define("Packt.model.staticData.Country",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "country",
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
      read : "countryService.read",
      create : "countryService.create",
      update : "countryService.update",
      destroy : "countryService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});