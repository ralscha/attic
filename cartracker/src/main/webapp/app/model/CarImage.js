Ext.define("CarTracker.model.CarImage",
{
  extend : "Ext.data.Model",
  fields : [ {
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
      destroy : "carImageService.destroy"
    }
  }
});