Ext.define('Packt.store.reports.SalesFilmCategory', {
	extend: 'Ext.data.Store',

	fields: [ {
		name: 'category'
	}, {
		name: 'totalSales'
	} ],

	autoLoad: false,

	proxy: {
		type: 'direct',
		directFn: 'reportService.read'
	}
});