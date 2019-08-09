Ext.define("Golb.model.Bookmark",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.identifier.Uuid", "Ext.data.proxy.Direct" ],
  identifier : "uuid",
  fields : [ {
    name : "id",
    type : "string",
    allowNull : true,
    convert : null
  }, {
    name : "title",
    type : "string",
    validators : [ {
      type : "notBlank"
    } ]
  }, {
    name : "url",
    type : "string",
    validators : [ {
      type : "url"
    }, {
      type : "notBlank"
    } ]
  }, {
    name : "remark",
    type : "string"
  }, "tags" ],
  proxy : {
    type : "direct",
    api : {
      read : "bookmarkService.read",
      create : "bookmarkService.update",
      update : "bookmarkService.update",
      destroy : "bookmarkService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});