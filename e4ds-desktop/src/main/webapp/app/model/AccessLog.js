Ext.define("E4desk.model.AccessLog",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "userName",
    type : "string"
  }, {
    name : "logIn",
    type : "date",
    dateFormat : "c"
  }, {
    name : "logOut",
    type : "date",
    dateFormat : "c"
  }, {
    name : "duration",
    type : "string"
  }, {
    name : "browser",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    directFn : "accessLogService.read",
    reader : {
      root : "records"
    }
  }
});