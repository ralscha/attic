Ext.define("Starter.model.setup.Section",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.identifier.Negative", "Ext.data.proxy.Direct", "Ext.data.validator.Length" ],
  identifier : "negative",
  fields : [ {
    name : "sectionName",
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
      read : "sectionService.read",
      create : "sectionService.create",
      update : "sectionService.update",
      destroy : "sectionService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});