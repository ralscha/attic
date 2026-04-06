Ext.define("MusicSearch.model.Song",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct" ],
  fields : [ {
    name : "id",
    type : "integer"
  }, {
    name : "title",
    type : "string"
  }, {
    name : "album",
    type : "string"
  }, {
    name : "artist",
    type : "string"
  }, {
    name : "year",
    type : "string"
  }, {
    name : "durationInSeconds",
    type : "integer"
  }, {
    name : "bitrate",
    type : "integer"
  }, {
    name : "encoding",
    type : "string"
  } ],
  proxy : {
    type : "direct",
    directFn : "searchService.search"
  }
});