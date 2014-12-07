Ext.define("CarTracker.model.option.Category",
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
      read : "categoryOptionService.read",
      create : "categoryOptionService.create",
      update : "categoryOptionService.update",
      destroy : "categoryOptionService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});