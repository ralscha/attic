Ext.define("Starter.model.inventory.SopDetail",
{
  extend : 'Starter.model.inventory.Base',
  requires : [ "Ext.data.proxy.Direct" ],
  identifier : "negative",
  fields : [ {
    name : "orderdate",
    type : "date",
    dateFormat : "Y-m-d"
  }, {
    name : "shipped",
    type : "boolean"
  }, {
    name : "sopHeaderId",
    type : "integer",
    reference: {
      parent: 'SopHeader'
    }
  }, {
    name : "id",
    type : "int"
  } ],
  proxy : {
    type : "direct",
    api: {
      read:    'sopdService.sopdetailRead',
      create:  'sopdService.sopdetailCreate',
      update:  'sopdService.sopdetailUpdate',
      destroy: 'sopdService.sopdetailDestroy'
    },

    writer : {
      writeAllFields : true
    }
  }
});