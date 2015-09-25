Ext.define("${jsAppNamespace}.model.Department",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct" ],
  fields : [ {
    name : "name",
    type : "string"
  }, {
    name : "id",
    type : "integer",
    allowNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    directFn : "departmentService.read",
    writer : {
      writeAllFields : true
    }
  }
});