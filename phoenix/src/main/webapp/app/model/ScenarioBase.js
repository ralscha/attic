Ext.define("Phoenix.model.ScenarioBase",
{
  extend : "Ext.data.Model",
  uses : [ "Phoenix.model.ScenarioItem" ],
  fields : [ {
    name : "name",
    type : "string"
  }, {
    name : "description",
    type : "string"
  }, {
    name : "dateUpdated",
    type : "date",
    dateFormat : "c"
  }, {
    name : "probabilityId",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "planCost",
    type : "float"
  }, {
    name : "impactCost",
    type : "float"
  }, {
    name : "impactLength",
    type : "float"
  }, {
    name : "totalImpact",
    type : "float"
  }, {
    name : "planEffectiveness",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  associations : [ {
    type : "hasMany",
    model : "Phoenix.model.ScenarioItem",
    associationKey : "scenarioItems",
    foreignKey : "scenarioId",
    name : "scenarioItems"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "scenarioService.read",
      create : "scenarioService.create",
      update : "scenarioService.update",
      destroy : "scenarioService.destroy"
    }
  }
});