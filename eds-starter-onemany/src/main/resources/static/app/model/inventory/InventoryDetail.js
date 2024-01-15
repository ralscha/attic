Ext.define("Starter.model.inventory.InventoryDetail",
{
  extend : "Starter.model.inventory.Base",
  requires : [ "Ext.data.identifier.Negative", "Ext.data.proxy.Direct", "Ext.data.validator.Length" ],
  identifier : "negative",
  fields : [ {
    name : "itemId",
    type : "integer",
    allowNull : true
  }, {
    name : "quantity",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "useItem",
    type : "boolean"
  }, {
    name : "inventoryHeaderId",
    type : "integer",
    allowNull : true,
    reference : {
      parent : "InventoryHeader"
    }
  }, {
    name : "id",
    type : "integer",
    allowNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "detailService.read",
      create : "detailService.create",
      update : "detailService.update",
      destroy : "detailService.destroy"
    },
    writer : {
      writeAllFields : true
    }
  }
});