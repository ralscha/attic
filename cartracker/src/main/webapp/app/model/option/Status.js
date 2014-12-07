Ext.define("CarTracker.model.option.Status",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "longName",
    type : "string"
  }, {
    name : "shortName",
    type : "string"
  }, {
    name : "createDate",
    type : "date",
    dateFormat : "c"
  }, {
    name : "active",
    type : "boolean",
    defaultValue : true
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "statusOptionService.read",
      create : "statusOptionService.create",
      update : "statusOptionService.update",
      destroy : "statusOptionService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});