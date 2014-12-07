Ext.define("Phoenix.model.ProbabilityBase",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "value",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    directFn : "probabilityService.read"
  }
});