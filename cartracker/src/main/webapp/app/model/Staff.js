Ext.define("CarTracker.model.Staff",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "firstName",
    type : "string"
  }, {
    name : "lastName",
    type : "string"
  }, {
    name : "dob",
    type : "date",
    dateFormat : "c"
  }, {
    name : "username",
    type : "string"
  }, {
    name : "changePassword",
    type : "string"
  }, {
    name : "address1",
    type : "string"
  }, {
    name : "address2",
    type : "string"
  }, {
    name : "city",
    type : "string"
  }, {
    name : "state",
    type : "string"
  }, {
    name : "postalCode",
    type : "string"
  }, {
    name : "phone",
    type : "string"
  }, {
    name : "hireDate",
    type : "date",
    dateFormat : "c"
  }, {
    name : "positionId",
    type : "int",
    useNull : true
  }, {
    name : "positionName",
    type : "string",
    persist : false
  }, "userRoleIds", {
    name : "createDate",
    type : "date",
    dateFormat : "c"
  }, {
    name : "active",
    type : "boolean",
    defaultValue : true
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "staffService.read",
      create : "staffService.create",
      update : "staffService.update",
      destroy : "staffService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});