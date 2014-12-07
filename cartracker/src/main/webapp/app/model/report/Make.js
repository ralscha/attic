Ext.define("CarTracker.model.report.Make",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "make",
    type : "string",
    persist : false
  }, {
    name : "model",
    type : "string",
    persist : false
  }, {
    name : "totalSales",
    type : "int",
    persist : false
  } ],
  proxy : {
    type : "direct",
    directFn : "reportService.readMakeReport"
  }
});