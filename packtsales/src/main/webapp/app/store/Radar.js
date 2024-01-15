Ext.define('Sales.store.Radar', {
	extend: 'Ext.data.Store',
	storeId: 'DashboardRadar',
	model: 'Sales.model.Radar',
	proxy: {
		type: 'direct',
		directFn: 'MyAppDashboard.getRadarData',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});
