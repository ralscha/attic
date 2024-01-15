Ext.define("BitP.model.LogEvent",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "eventDate",
    type : "date",
    dateFormat : "c"
  }, {
    name : "level",
    type : "string"
  }, {
    name : "logger",
    type : "string"
  }, {
    name : "source",
    type : "string"
  }, {
    name : "message",
    type : "string"
  }, {
    name : "marker",
    type : "string"
  }, {
    name : "thread",
    type : "string"
  }, {
    name : "exception",
    type : "string"
  }, {
    name : "userName",
    type : "string"
  }, {
    name : "ip",
    type : "string"
  }, {
    name : "userAgent",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    directFn : "logEventService.read",
    reader : {
      root : "records"
    }
  }
});