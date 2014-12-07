Ext.define("Packt.model.staticData.Language",
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
      read : "languageService.read",
      create : "languageService.create",
      update : "languageService.update",
      destroy : "languageService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});