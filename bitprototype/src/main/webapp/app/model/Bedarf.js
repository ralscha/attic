Ext.define("BitP.model.Bedarf",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "titel",
    type : "string"
  }, {
    name : "anforderungen",
    type : "string"
  }, {
    name : "arbeitsdauerBeginn",
    type : "date",
    dateFormat : "c"
  }, {
    name : "arbeitsdauerEnde",
    type : "date",
    dateFormat : "c"
  }, {
    name : "arbeitsOrt",
    type : "string"
  }, {
    name : "kostendach",
    type : "float"
  }, {
    name : "einreichefrist",
    type : "date",
    dateFormat : "c"
  }, {
    name : "status",
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
      read : "bedarfService.read",
      create : "bedarfService.create",
      update : "bedarfService.update",
      destroy : "bedarfService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});