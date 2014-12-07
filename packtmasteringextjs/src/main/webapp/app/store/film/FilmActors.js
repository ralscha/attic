Ext.define('Packt.store.film.FilmActors', {
	extend: 'Ext.data.Store',

	requires: [ 'Packt.model.staticData.Actor' ],

	model: 'Packt.model.staticData.Actor'
});