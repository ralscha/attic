Ext.define("CarTracker.model.option.Feature",
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
      read : "featureOptionService.read",
      create : "featureOptionService.create",
      update : "featureOptionService.update",
      destroy : "featureOptionService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});