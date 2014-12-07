Ext.define("CarTracker.model.Workflow",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "notes",
    type : "string"
  }, {
    name : "approved",
    type : "boolean"
  }, {
    name : "lastStatusName",
    type : "string",
    persist : false
  }, {
    name : "nextStatusName",
    type : "string",
    persist : false
  }, {
    name : "lastName",
    type : "string",
    persist : false
  }, {
    name : "firstName",
    type : "string",
    persist : false
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
    directFn : "workflowService.read"
  }
});