Ext.define('Sales.store.Pie', {
	extend: 'Ext.data.Store',
	storeId: 'DashboardPie',
	model: 'Sales.model.Pie',
	proxy: {
		type: 'direct',
		directFn: 'MyAppDashboard.getPieData',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});
