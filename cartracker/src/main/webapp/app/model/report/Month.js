Ext.define("CarTracker.model.report.Month",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "month",
    type : "string",
    persist : false
  }, {
    name : "totalSold",
    type : "int",
    persist : false
  }, {
    name : "totalSales",
    type : "int",
    persist : false
  } ],
  proxy : {
    type : "direct",
    directFn : "reportService.readMonthReport"
  }
});