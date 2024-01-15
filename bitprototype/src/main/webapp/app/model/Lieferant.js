Ext.define("BitP.model.Lieferant",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "firma",
    type : "string"
  }, {
    name : "zusatz",
    type : "string"
  }, {
    name : "strasse",
    type : "string"
  }, {
    name : "plz",
    type : "string"
  }, {
    name : "ort",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "lieferantService.read",
      create : "lieferantService.create",
      update : "lieferantService.update",
      destroy : "lieferantService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});