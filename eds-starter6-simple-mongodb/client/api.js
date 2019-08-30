var REMOTING_API = {
  "url" : "router",
  "type" : "remoting",
  "actions" : {
    "departmentService" : [ {
      "name" : "read",
      "len" : 1
    } ],
    "formLoadService" : [ {
      "name" : "getFormData",
      "len" : 1
    }, {
      "name" : "getRemark",
      "len" : 0
    } ],
    "formSubmitService" : [ {
      "name" : "handleFormSubmit",
      "len" : 0,
      "formHandler" : true
    } ],
    "treeLoadService" : [ {
      "name" : "getTree",
      "len" : 1
    } ],
    "userService" : [ {
      "name" : "create",
      "len" : 1
    }, {
      "name" : "destroy",
      "len" : 1
    }, {
      "name" : "read",
      "len" : 1
    }, {
      "name" : "update",
      "len" : 1
    } ]
  }
};

var POLLING_URLS = {
  "chart" : "poll/pollService/currentTime/chart"
};