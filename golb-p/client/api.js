var REMOTING_API = {
  "url" : "router",
  "type" : "remoting",
  "actions" : {
    "binaryService" : [ {
      "name" : "destroy",
      "len" : 1
    }, {
      "name" : "read",
      "len" : 1
    }, {
      "name" : "update",
      "len" : 1
    } ],
    "feedbackService" : [ {
      "name" : "destroy",
      "len" : 1
    }, {
      "name" : "read",
      "len" : 1
    } ],
    "logService" : [ {
      "name" : "logClientCrash",
      "len" : 1
    } ],
    "navigationService" : [ {
      "name" : "getNavigation",
      "len" : 1
    } ],
    "postService" : [ {
      "name" : "destroy",
      "len" : 1
    }, {
      "name" : "isSlugUnique",
      "len" : 2
    }, {
      "name" : "publish",
      "len" : 1
    }, {
      "name" : "read",
      "len" : 1
    }, {
      "name" : "unpublish",
      "len" : 1
    }, {
      "name" : "update",
      "len" : 1
    } ],
    "securityService" : [ {
      "name" : "disableScreenLock",
      "len" : 0,
      "formHandler" : true
    }, {
      "name" : "enableScreenLock",
      "len" : 0
    }, {
      "name" : "getAuthUser",
      "len" : 0
    }, {
      "name" : "reset",
      "len" : 0,
      "formHandler" : true
    }, {
      "name" : "resetRequest",
      "len" : 0,
      "formHandler" : true
    }, {
      "name" : "signin2fa",
      "len" : 0,
      "formHandler" : true
    }, {
      "name" : "switchUser",
      "len" : 1
    } ],
    "tagService" : [ {
      "name" : "destroy",
      "len" : 1
    }, {
      "name" : "read",
      "len" : 1
    }, {
      "name" : "readForCombo",
      "len" : 1
    }, {
      "name" : "update",
      "len" : 1
    } ],
    "urlCheckService" : [ {
      "name" : "check",
      "len" : 0
    }, {
      "name" : "read",
      "len" : 1
    } ],
    "userConfigService" : [ {
      "name" : "destroyPersistentLogin",
      "len" : 1
    }, {
      "name" : "disable2f",
      "len" : 0
    }, {
      "name" : "enable2f",
      "len" : 0
    }, {
      "name" : "readPersistentLogins",
      "len" : 1
    }, {
      "name" : "readSettings",
      "len" : 1
    }, {
      "name" : "updateSettings",
      "len" : 1
    } ],
    "userService" : [ {
      "name" : "destroy",
      "len" : 1
    }, {
      "name" : "disableTwoFactorAuth",
      "len" : 1
    }, {
      "name" : "read",
      "len" : 1
    }, {
      "name" : "sendPassordResetEmail",
      "len" : 1
    }, {
      "name" : "unlock",
      "len" : 1
    }, {
      "name" : "update",
      "len" : 1
    } ]
  }
};

var POLLING_URLS = {
  "heartbeat" : "poll/securityService/heartbeat/heartbeat"
};