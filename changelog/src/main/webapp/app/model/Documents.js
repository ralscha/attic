Ext.define("Changelog.model.Documents",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "customerId",
    type : "int"
  }, {
    name : "description",
    type : "string"
  }, {
    name : "contentType",
    type : "string"
  }, {
    name : "size",
    type : "int"
  }, {
    name : "fileName",
    type : "string"
  }, {
    name : "date",
    type : "date",
    dateFormat : "d.m.Y H:i:s"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "customerService.loadDocuments",
      destroy : "customerService.destroyDocument"
    }
  }
});