Ext.define("BitP.model.Offerte",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "name",
    type : "string"
  }, {
    name : "vorname",
    type : "string"
  }, {
    name : "jahrgang",
    type : "int",
    useNull : true
  }, {
    name : "bemerkungen",
    type : "string"
  }, {
    name : "zurverfuegungStellungBeginn",
    type : "date",
    dateFormat : "c"
  }, {
    name : "zurverfuegungStellungEnde",
    type : "date",
    dateFormat : "c"
  }, {
    name : "pensum",
    type : "int",
    useNull : true
  }, {
    name : "stundensatz",
    type : "float",
    useNull : true
  }, {
    name : "status",
    type : "string"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "offerteService.read",
      create : "offerteService.create",
      update : "offerteService.update",
      destroy : "offerteService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});