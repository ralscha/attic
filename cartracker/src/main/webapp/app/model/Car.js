Ext.define("CarTracker.model.Car",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "description",
    type : "string"
  }, {
    name : "year",
    type : "int",
    useNull : true
  }, {
    name : "listPrice",
    type : "int",
    useNull : true
  }, {
    name : "salePrice",
    type : "int",
    useNull : true
  }, {
    name : "acquisitionDate",
    type : "date",
    dateFormat : "c"
  }, {
    name : "saleDate",
    type : "date",
    dateFormat : "c"
  }, {
    name : "stockNumber",
    type : "string"
  }, {
    name : "vin",
    type : "string"
  }, {
    name : "fuel",
    type : "string"
  }, {
    name : "engine",
    type : "string"
  }, {
    name : "transmission",
    type : "string"
  }, {
    name : "mileage",
    type : "int",
    useNull : true
  }, {
    name : "sold",
    type : "boolean",
    defaultValue : false
  }, {
    name : "statusId",
    type : "int",
    useNull : true
  }, {
    name : "statusName",
    type : "string",
    persist : false
  }, {
    name : "makeId",
    type : "int",
    useNull : true
  }, {
    name : "makeName",
    type : "string",
    persist : false
  }, {
    name : "modelId",
    type : "int",
    useNull : true
  }, {
    name : "modelName",
    type : "string",
    persist : false
  }, {
    name : "categoryId",
    type : "int",
    useNull : true
  }, {
    name : "categoryName",
    type : "string",
    persist : false
  }, {
    name : "colorId",
    type : "int",
    useNull : true
  }, {
    name : "colorName",
    type : "string",
    persist : false
  }, {
    name : "driveTrainId",
    type : "int",
    useNull : true
  }, "featureIds", "salesPeopleIds", "carImageIds", {
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
      read : "carService.readCars",
      create : "carService.create",
      update : "carService.update",
      destroy : "carService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});