Ext.define("BitP.model.OfferteWorkflow",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "createDate",
    type : "date",
    dateFormat : "c"
  }, {
    name : "lastStatus",
    type : "string"
  }, {
    name : "nextStatus",
    type : "string"
  }, {
    name : "notes",
    type : "string"
  }, {
    name : "lastName",
    type : "string",
    persist : false
  }, {
    name : "firstName",
    type : "string",
    persist : false
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    directFn : "workflowService.readOfferte"
  }
});