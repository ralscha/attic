Ext.define("Golb.model.Feedback",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct", "Ext.data.identifier.Uuid" ],
  identifier : "uuid",
  fields : [ {
    name : "id",
    type : "string",
    allowNull : true,
    convert : null
  }, {
    name : "postId",
    type : "string"
  }, {
    name : "body",
    type : "string"
  }, {
    name : "replyEmailAddress",
    type : "string"
  }, {
    name : "postTitle",
    type : "string"
  }, {
    name : "created",
    type : "date",
    dateFormat : "time"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "feedbackService.read",
      destroy : "feedbackService.destroy"
    },
    reader : {
      rootProperty : "records"
    },
    writer : {
      writeAllFields : true
    }
  }
});