Ext.define("Addressbook.model.ContactInfo",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "contactId",
    type : "int"
  }, {
    name : "infoType",
    type : "string"
  }, {
    name : "phone",
    type : "string"
  }, {
    name : "email",
    type : "string"
  }, {
    name : "address",
    type : "string"
  }, {
    name : "city",
    type : "string"
  }, {
    name : "state",
    type : "string"
  }, {
    name : "zip",
    type : "string"
  }, {
    name : "country",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true
  } ],
  proxy : {
    type : "direct",
    pageParam : undefined,
    startParam : undefined,
    limitParam : undefined,
    api : {
      read : "contactInfoService.readContactInfo",
      create : "contactInfoService.create",
      update : "contactInfoService.update",
      destroy : "contactInfoService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});