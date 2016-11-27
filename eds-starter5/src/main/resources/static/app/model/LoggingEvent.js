Ext.define("Starter.model.LoggingEvent",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct" ],
  fields : [ {
    name : "id",
    type : "integer"
  }, {
    name : "dateTime",
    type : "date",
    dateFormat : "time"
  }, {
    name : "message",
    type : "string"
  }, {
    name : "level",
    type : "string"
  }, {
    name : "callerClass",
    type : "string"
  }, {
    name : "callerLine",
    type : "string"
  }, {
    name : "ip",
    type : "string"
  }, {
    name : "stacktrace",
    type : "string"
  } ],
  proxy : {
    type : "direct",
    directFn : "loggingEventService.read",
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});