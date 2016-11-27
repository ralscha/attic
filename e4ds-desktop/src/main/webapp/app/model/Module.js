Ext.define("E4desk.model.Module",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "id",
    type : "string"
  }, {
    name : "name",
    type : "string"
  }, {
    name : "iconCls",
    type : "string"
  }, {
    name : "showOnDesktop",
    type : "boolean"
  }, {
    name : "menuPath",
    type : "string"
  } ],
  proxy : {
    type : "direct",
    directFn : "moduleService.read"
  }
});