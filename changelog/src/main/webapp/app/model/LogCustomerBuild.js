Ext.define('Changelog.model.LogCustomerBuild', {
	extend: 'Ext.data.Model',
	fields: [ {
		name: 'id',
		type: 'int'
	}, {
		name: 'versionNumber',
		type: 'string'
	}, {
		name: 'versionDate',
		type: 'date',
		dateFormat: 'd.m.Y'
	}, {
		name: 'internalBuild',
		type: 'boolean'
	} ],

	proxy: {
		type: 'direct',
		directFn: 'logService.loadCustomerBuild'
	}
});