Ext.define('Sales.store.Bill', {
	extend: 'Ext.data.Store',
	storeId: 'BillList',
	model: 'Sales.model.Bill',
	remoteSort: true,
	pageSize: 100,
	proxy: {
		type: 'direct',
		directFn: 'MyAppBill.getGrid',
		reader: {
			type: 'json',
			root: 'items',
			totalProperty: 'total'
		}
	}
});
