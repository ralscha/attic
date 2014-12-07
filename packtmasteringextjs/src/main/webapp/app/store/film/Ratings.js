Ext.define('Packt.store.film.Ratings', {
	extend: 'Ext.data.ArrayStore',

	fields: [ {
		name: 'text'
	} ],

	data: [ [ 'G' ], [ 'PG' ], [ 'PG-13' ], [ 'R' ], [ 'NC-17' ] ],

	autoLoad: true
});