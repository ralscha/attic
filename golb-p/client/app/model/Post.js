Ext.define("Golb.model.Post",
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
    name : "slug",
    type : "string",
    validators : [ {
      type : "notBlank"
    } ]
  }, {
    name : "body",
    type : "string"
  }, {
    name : "summary",
    type : "string"
  }, {
    name : "bodyHtml",
    type : "string"
  }, "tags", {
    name : "created",
    type : "date",
    dateFormat : "time",
    persist : false
  }, {
    name : "updated",
    type : "date",
    dateFormat : "time",
    persist : false
  }, {
    name : "published",
    type : "date",
    dateFormat : "time",
    persist : false
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "postService.read",
      create : "postService.update",
      update : "postService.update",
      destroy : "postService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});