Ext.define('Sales.store.Line', {
	extend: 'Ext.data.Store',
	storeId: 'DashboardLine',
	model: 'Sales.model.Line',
	proxy: {
		type: 'direct',
		directFn: 'MyAppDashboard.getLineData',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});
