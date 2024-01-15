Ext.define("Changelog.model.Change",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "bugNumber",
    type : "string"
  }, {
    name : "module",
    type : "string"
  }, {
    name : "subject",
    type : "string"
  }, {
    name : "description",
    type : "string"
  }, {
    name : "implementationDate",
    type : "date",
    dateFormat : "d.m.Y"
  }, {
    name : "typ",
    type : "string"
  }, {
    name : "noOfCustomers",
    type : "int",
    persist : false
  }, {
    name : "customerTooltip",
    type : "string",
    persist : false
  }, "customerIds", {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "changeService.load",
      create : "changeService.create",
      update : "changeService.update",
      destroy : "changeService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});