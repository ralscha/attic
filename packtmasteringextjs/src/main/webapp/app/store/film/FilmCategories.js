Ext.define('Packt.store.film.FilmCategories', {
	extend: 'Ext.data.Store',

	requires: [ 'Packt.model.staticData.Category' ],

	model: 'Packt.model.staticData.Category'
});