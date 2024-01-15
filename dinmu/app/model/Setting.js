Ext.define('Dinmu.model.Setting', {
	extend: 'Ext.data.Model',
	requires: [ 'Ext.data.identifier.Uuid' ],

	config: {
		idProperty: 'id',
		identifier: 'uuid',
		fields: [ {
			name: 'id',
			type: 'string'
		}, {
			name: 'city',
			type: 'string'
		}, {
			name: 'country',
			type: 'string'
		}, {
			name: 'units',
			type: 'string'
		}, {
			name: 'geo',
			type: 'boolean'
		} ],
		validations: [ {
			type: 'presence',
			field: 'city',
			message: 'Please provide a city.'
		}, {
			type: 'presence',
			field: 'country',
			message: 'Please provide a country.'
		} ],
		proxy: {
			type: 'localstorage',
			id: 'weathersettings'
		}
	}
});
