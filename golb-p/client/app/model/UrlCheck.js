Ext.define("Golb.model.UrlCheck",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct" ],
  fields : [ {
    name : "url",
    type : "string"
  }, {
    name : "post",
    type : "string"
  }, {
    name : "status",
    type : "integer"
  }, {
    name : "successful",
    type : "boolean"
  } ],
  proxy : {
    type : "direct",
    directFn : "urlCheckService.read",
    writer : {
      writeAllFields : true
    }
  }
});