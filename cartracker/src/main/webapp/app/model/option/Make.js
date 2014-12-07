Ext.define("CarTracker.model.option.Make",
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
      read : "makeOptionService.read",
      create : "makeOptionService.create",
      update : "makeOptionService.update",
      destroy : "makeOptionService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});