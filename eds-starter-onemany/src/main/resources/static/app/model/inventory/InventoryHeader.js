Ext.define("Starter.model.inventory.InventoryHeader",
{
  extend : "Starter.model.inventory.Base",
  requires : [ "Ext.data.identifier.Negative", "Ext.data.proxy.Direct", "Ext.data.validator.Length" ],
  identifier : "negative",
  fields : [ {
    name : "userName",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "enrollNo",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "departmentId",
    type : "integer",
    allowNull : true
  }, {
    name : "sectionId",
    type : "integer",
    allowNull : true
  }, {
    name : "locationId",
    type : "integer",
    allowNull : true
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
    name : "id",
    type : "integer",
    allowNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "headerService.read",
      create : "headerService.create",
      update : "headerService.update",
      destroy : "headerService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true,
      allDataOptions : {
        associated : true,
        persist : true
      },
      partialDataOptions : {
        associated : true,
        persist : true
      }
    }
  }
});