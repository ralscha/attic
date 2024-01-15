Ext.define('Sales.store.QuotationItem', {
	extend: 'Ext.data.Store',
	storeId: 'QuotationItem',
	fields: [ 'id', 'desc', 'qty', 'price', 'sum' ],
	data: {
		'items': []
	},
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	}
});
