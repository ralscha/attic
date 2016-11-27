Ext.define("SimpleApp.model.Department",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct" ],
  fields : [ {
    name : "id",
    type : "string"
  }, {
    name : "name",
    type : "string"
  } ],
  proxy : {
    type : "direct",
    directFn : "departmentService.read",
    writer : {
      writeAllFields : true
    }
  }
});