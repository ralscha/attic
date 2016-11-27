Ext.define("Starter.model.AccessLog",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct", "Ext.data.validator.Length" ],
  fields : [ {
    name : "loginName",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "loginTimestamp",
    type : "date",
    dateFormat : "time"
  }, {
    name : "userAgentName",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 20
    } ]
  }, {
    name : "userAgentVersion",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 10
    } ]
  }, {
    name : "operatingSystem",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 20
    } ]
  }, {
    name : "ipAddress",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 45
    } ]
  }, {
    name : "location",
    type : "string",
    validators : [ {
      type : "length",
      min : 0,
      max : 255
    } ]
  }, {
    name : "id",
    type : "integer",
    allowNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    directFn : "accessLogService.read",
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});