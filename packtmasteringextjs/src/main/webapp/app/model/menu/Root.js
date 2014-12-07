Ext.define("Packt.model.menu.Root",
{
  extend : "Ext.data.Model",
  uses : [ "Packt.model.menu.Item" ],
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
    name : "leaf",
    type : "boolean"
  }, {
    name : "checked",
    type : "boolean",
    useNull : true
  }, {
    name : "expanded",
    type : "boolean",
    useNull : true
  } ],
  associations : [ {
    type : "hasMany",
    model : "Packt.model.menu.Item",
    associationKey : "children",
    foreignKey : "root_id",
    name : "children"
  } ],
  proxy : {
    type : "direct",
    directFn : "menuService.read"
  }
});