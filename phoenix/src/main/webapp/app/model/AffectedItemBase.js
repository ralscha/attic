Ext.define("Phoenix.model.AffectedItemBase",
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
    directFn : "affectedItemService.read"
  }
});