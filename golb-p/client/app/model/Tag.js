Ext.define("Golb.model.Tag",
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
    name : "name",
    type : "string",
    validators : [ {
      type : "notBlank"
    } ]
  }, {
    name : "noOfPosts",
    type : "integer",
    persist : false
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "tagService.read",
      create : "tagService.update",
      update : "tagService.update",
      destroy : "tagService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});