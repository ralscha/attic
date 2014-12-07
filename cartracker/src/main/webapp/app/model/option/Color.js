Ext.define("CarTracker.model.option.Color",
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
      read : "colorOptionService.read",
      create : "colorOptionService.create",
      update : "colorOptionService.update",
      destroy : "colorOptionService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});