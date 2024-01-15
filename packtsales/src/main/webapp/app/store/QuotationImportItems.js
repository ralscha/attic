Ext.define('Sales.store.QuotationImportItems', {
	extend: 'Ext.data.Store',
	storeId: 'QuotationImportItems',
	fields: [ 'id', 'status', 'parent', 'description', 'qty', 'price', 'sum', 'modified', 'created' ],
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
