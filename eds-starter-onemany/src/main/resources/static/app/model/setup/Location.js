Ext.define("Starter.model.setup.Location",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.identifier.Negative", "Ext.data.proxy.Direct", "Ext.data.validator.Length" ],
  identifier : "negative",
  fields : [ {
    name : "locationName",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "notes",
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
    name : "id",
    type : "integer",
    allowNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "locationService.read",
      create : "locationService.create",
      update : "locationService.update",
      destroy : "locationService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});