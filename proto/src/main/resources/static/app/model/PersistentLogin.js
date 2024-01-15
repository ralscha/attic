Ext.define("Proto.model.PersistentLogin",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct" ],
  idProperty : "series",
  fields : [ {
    name : "series",
    type : "string"
  }, {
    name : "lastUsed",
    type : "date"
  }, {
    name : "ipAddress",
    type : "string"
  }, {
    name : "userAgent",
    type : "string"
  } ],
  proxy : {
    type : "direct",
    idParam : "series",
    api : {
      read : "securityService.readPersistentLogins",
      destroy : "securityService.destroyPersistentLogin"
    }
  }
});