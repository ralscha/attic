Ext.define("Starter.model.setup.Item",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.identifier.Negative", "Ext.data.proxy.Direct", "Ext.data.validator.Length" ],
  identifier : "negative",
  fields : [ {
    name : "code",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "name",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "remark",
    type : "string",
    allowNull : true,
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "lastUpdate",
    type : "date",
    dateFormat : "time",
    persist : false
  }, {
    name : "uomId",
    type : "integer",
    allowNull : true
  }, {
    name : "id",
    type : "integer",
    allowNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "itemService.read",
      create : "itemService.create",
      update : "itemService.update",
      destroy : "itemService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});