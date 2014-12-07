Ext.define("CarTracker.model.option.DriveTrain",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "longName",
    type : "string"
  }, {
    name : "shortName",
    type : "string"
  }, {
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
      read : "driveTrainOptionService.read",
      create : "driveTrainOptionService.create",
      update : "driveTrainOptionService.update",
      destroy : "driveTrainOptionService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});