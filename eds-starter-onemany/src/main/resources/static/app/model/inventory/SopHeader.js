Ext.define("Starter.model.inventory.SopHeader",
{
  extend : 'Starter.model.inventory.Base',
  requires : [ "Ext.data.identifier.Negative", "Ext.data.proxy.Direct", "Ext.data.validator.Length" ],
  identifier : "negative",
  fields : [ {
    name : "name",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 50
    } ]
  }, {
    name : "phone",
    type : "string",
    convert : null,
    validators : [ {
      type : "length",
      min : 0,
      max : 50
    } ]
  }, {
    name : "id",
    type : "int"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "sophService.sopheaderRead",
      create : "sophService.sopheaderCreate",
      update : "sophService.sopheaderUpdate",
      destroy : "sophService.sopheaderDestroy"
    },
    writer : {
      writeAllFields : true
    }
  }
});