Ext.define("TTT.model.Task",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "id",
    type : "int"
  }, {
    name : "name",
    type : "string"
  } ],
  validations : [ {
    type : "presence",
    field : "name"
  }, {
    type : "length",
    field : "name",
    min : 1,
    max : 200
  } ]
});