Ext.define("Proto.model.Project",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct", "Ext.data.identifier.Negative" ],
  identifier : "negative",
  fields : [ {
    name : "name",
    type : "string"
  }, {
    name : "ms1",
    type : "date",
    dateFormat : "Y-m-d"
  }, {
    name : "ms2",
    type : "date",
    dateFormat : "Y-m-d"
  }, {
    name : "ms3",
    type : "date",
    dateFormat : "Y-m-d"
  }, {
    name : "ms4",
    type : "date",
    dateFormat : "Y-m-d"
  }, {
    name : "ms5",
    type : "date",
    dateFormat : "Y-m-d"
  }, {
    name : "ms6",
    type : "date",
    dateFormat : "Y-m-d"
  }, {
    name : "id",
    type : "integer"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "projectService.read",
      create : "projectService.update",
      update : "projectService.update",
      destroy : "projectService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});