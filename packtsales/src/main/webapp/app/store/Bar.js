Ext.define('Sales.store.Bar', {
	extend: 'Ext.data.Store',
	storeId: 'DashboardBar',
	model: 'Sales.model.Bar',
	proxy: {
		type: 'direct',
		directFn: 'MyAppDashboard.getBarData',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});
