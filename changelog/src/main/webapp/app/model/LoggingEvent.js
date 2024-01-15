Ext.define("Changelog.model.LoggingEvent",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "id",
    type : "int"
  }, {
    name : "dateTime",
    type : "date",
    dateFormat : "Y-m-d H:i:s"
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
      root : "records"
    }
  }
});