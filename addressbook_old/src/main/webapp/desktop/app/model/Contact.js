Ext.define("Addressbook.model.Contact",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "contactGroupIds",
    type : "auto"
  }, {
    name : "firstName",
    type : "string"
  }, {
    name : "lastName",
    type : "string"
  }, {
    name : "dateOfBirth",
    type : "date",
    dateFormat : "c"
  }, {
    name : "notes",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "contactService.read",
      create : "contactService.create",
      update : "contactService.update",
      destroy : "contactService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});