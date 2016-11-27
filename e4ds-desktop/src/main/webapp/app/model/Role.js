Ext.define("E4desk.model.Role",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "name",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    directFn : "userService.readRoles"
  }
});