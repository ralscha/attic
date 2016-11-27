Ext.define("E4desk.model.Wallpaper",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "text",
    type : "string"
  }, {
    name : "img",
    type : "string"
  }, {
    name : "width",
    type : "int"
  }, {
    name : "height",
    type : "int"
  } ],
  proxy : {
    type : "direct",
    directFn : "wallpaperService.read"
  }
});