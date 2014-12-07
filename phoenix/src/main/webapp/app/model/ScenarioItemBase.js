Ext.define("Phoenix.model.ScenarioItemBase",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "affectedItemId",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "itemDescription",
    type : "string"
  }, {
    name : "timeToRecover",
    type : "float"
  }, {
    name : "cost",
    type : "float"
  }, {
    name : "revenueImpactId",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "scenarioId",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ]
});