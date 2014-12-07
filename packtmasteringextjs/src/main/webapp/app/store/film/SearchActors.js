Ext.define('Packt.store.film.SearchActors', {
	extend: 'Ext.data.Store',
	requires: [ 'Packt.model.staticData.Actor' ],
	model: 'Packt.model.staticData.Actor',
	pageSize: 2,
	remoteFilter: true
});