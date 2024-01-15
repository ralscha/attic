Ext.define("Changelog.model.CustomerBuild",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "versionNumber",
    type : "string"
  }, {
    name : "internalBuild",
    type : "boolean"
  }, {
    name : "versionDate",
    type : "date",
    dateFormat : "d.m.Y"
  }, {
    name : "customerId",
    type : "int"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "customerService.loadBuilds",
      create : "customerService.createBuild",
      update : "customerService.updateBuild",
      destroy : "customerService.destroyBuild"
    }
  }
});