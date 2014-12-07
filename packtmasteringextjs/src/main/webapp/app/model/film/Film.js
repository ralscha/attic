Ext.define("Packt.model.film.Film",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "title",
    type : "string"
  }, {
    name : "description",
    type : "string"
  }, {
    name : "releaseYear",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "languageId",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "originalLanguageId",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "rentalDuration",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "rentalRate",
    type : "float",
    useNull : true,
    convert : null
  }, {
    name : "length",
    type : "int",
    useNull : true,
    convert : null
  }, {
    name : "replacementCost",
    type : "float",
    useNull : true,
    convert : null
  }, {
    name : "rating",
    type : "string"
  }, {
    name : "specialFeatures",
    type : "string"
  }, "filmCategoryIds", "filmActorIds", {
    name : "lastUpdate",
    type : "date",
    dateFormat : "c"
  }, {
    name : "id",
    type : "int",
    useNull : true,
    convert : null
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "filmService.read",
      create : "filmService.create",
      update : "filmService.update",
      destroy : "filmService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});