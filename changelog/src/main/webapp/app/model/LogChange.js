Ext.define('Changelog.model.LogChange', {
	extend: 'Ext.data.Model',
	fields: [ {
		name: 'bugNumber',
		type: 'string'
	}, {
		name: 'module',
		type: 'string'
	}, {
		name: 'description',
		type: 'string'
	}, {
		name: 'subject',
		type: 'string'
	}, {
		name: 'typ',
		type: 'string'
	}],

	proxy: {
		type: 'direct',
		directFn: 'logService.loadChanges'
	}
});