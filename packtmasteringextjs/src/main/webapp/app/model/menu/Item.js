Ext.define("Packt.model.menu.Item",
{
  extend : "Ext.data.Model",
  uses : [ "Packt.model.menu.Root" ],
  fields : [ {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "text",
    type : "string"
  }, {
    name : "iconCls",
    type : "string"
  }, {
    name : "className",
    type : "string"
  }, {
    name : "root_id",
    type : "int"
  }, {
    name : "leaf",
    type : "boolean"
  }, {
    name : "checked",
    type : "boolean",
    useNull : true
  } ],
  associations : [ {
    type : "belongsTo",
    model : "Packt.model.menu.Root",
    associationKey : "root",
    foreignKey : "root_id",
    setterName : "setRoot",
    getterName : "getRoot"
  } ]
});