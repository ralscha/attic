Ext.define('Changelog.store.TypeChart', {
	extend: 'Ext.data.Store',
	autoLoad: false,
	fields: [ {
		name: 'type',
		type: 'string'
	}, {
		name: 'number',
		type: 'int'
	} ],
	proxy: {
		type: 'direct',
		directFn: reportService.loadTypeChartData
	}
});
