Ext.define('Changelog.store.TypePerYearChart', {
	extend: 'Ext.data.Store',
	autoLoad: false,
	fields: [ {
		name: 'month',
		type: 'string'
	}, {
		name: 'fix',
		type: 'int'
	}, {
		name: 'enhancement',
		type: 'int'
	}, {
		name: 'new',
		type: 'int'
	} ],
	proxy: {
		type: 'direct',
		directFn: reportService.loadTypePerYearChartData
	}
});
