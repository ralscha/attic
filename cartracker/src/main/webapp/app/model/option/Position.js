Ext.define("CarTracker.model.option.Position",
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
      read : "positionOptionService.read",
      create : "positionOptionService.create",
      update : "positionOptionService.update",
      destroy : "positionOptionService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});