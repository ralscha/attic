Ext.define("TTT.model.TaskLog",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "id",
    type : "int"
  }, {
    name : "taskDescription",
    type : "string"
  }, {
    name : "taskLogDate",
    type : "date"
  }, {
    name : "taskMinutes",
    type : "int"
  } ],
  validations : [ {
    type : "presence",
    field : "taskDescription"
  }, {
    type : "length",
    field : "taskDescription",
    min : 1,
    max : 2000
  }, {
    type : "presence",
    field : "taskLogDate"
  }, {
    type : "presence",
    field : "taskMinutes"
  } ]
});