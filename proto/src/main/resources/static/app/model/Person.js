Ext.define("Proto.model.Person",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct", "Ext.data.identifier.Negative" ],
  identifier : "negative",
  fields : [ {
    name : "firstName",
    type : "string"
  }, {
    name : "lastName",
    type : "string"
  }, {
    name : "id",
    type : "integer"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "personService.read",
      create : "personService.update",
      update : "personService.update",
      destroy : "personService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});