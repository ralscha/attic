Ext.define("Packt.model.staticData.Actor",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "firstName",
    type : "string"
  }, {
    name : "lastName",
    type : "string"
  }, {
    name : "filmInfo",
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
    api : {
      read : "actorService.readActor",
      create : "actorService.create",
      update : "actorService.update",
      destroy : "actorService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});