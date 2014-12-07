Ext.define("CarTracker.model.option.Model",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "makeId",
    type : "int",
    useNull : true
  }, {
    name : "makeName",
    type : "string",
    persist : false
  }, {
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
      read : "modelOptionService.read",
      create : "modelOptionService.create",
      update : "modelOptionService.update",
      destroy : "modelOptionService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});