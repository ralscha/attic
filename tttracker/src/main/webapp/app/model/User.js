Ext.define("TTT.model.User",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "id",
    type : "int"
  }, {
    name : "userName",
    type : "string"
  }, {
    name : "firstName",
    type : "string"
  }, {
    name : "lastName",
    type : "string"
  }, {
    name : "email",
    type : "string"
  }, {
    name : "password",
    type : "string"
  }, {
    name : "adminRole",
    type : "string"
  } ],
  validations : [ {
    type : "presence",
    field : "userName"
  }, {
    type : "length",
    field : "userName",
    min : 1,
    max : 50
  }, {
    type : "presence",
    field : "firstName"
  }, {
    type : "length",
    field : "firstName",
    min : 1,
    max : 100
  }, {
    type : "presence",
    field : "lastName"
  }, {
    type : "length",
    field : "lastName",
    min : 1,
    max : 100
  }, {
    type : "presence",
    field : "email"
  }, {
    type : "email",
    field : "email"
  }, {
    type : "length",
    field : "email",
    min : 1,
    max : 100
  }, {
    type : "presence",
    field : "password"
  }, {
    type : "length",
    field : "password",
    min : 1,
    max : 100
  }, {
    type : "inclusion",
    field : "adminRole",
    list : ['Y','N']
  } ]
});