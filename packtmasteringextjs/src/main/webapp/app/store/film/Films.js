Ext.define('Packt.store.film.Films', {
	extend: 'Ext.data.Store',

	requires: [ 'Packt.model.film.Film' ],

	model: 'Packt.model.film.Film',
	pageSize: 20,
	storeId: 'films'
});