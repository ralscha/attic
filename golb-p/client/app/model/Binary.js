Ext.define("Golb.model.Binary",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct", "Ext.data.identifier.Uuid" ],
  identifier : "uuid",
  fields : [ {
    name : "id",
    type : "string",
    allowNull : true,
    convert : null
  }, {
    name : "name",
    type : "string"
  }, {
    name : "type",
    type : "string"
  }, {
    name : "size",
    type : "integer"
  }, {
    name : "data",
    type : "string"
  }, {
    name : "lastModifiedDate",
    type : "date",
    dateFormat : "time"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "binaryService.read",
      create : "binaryService.update",
      update : "binaryService.update",
      destroy : "binaryService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});