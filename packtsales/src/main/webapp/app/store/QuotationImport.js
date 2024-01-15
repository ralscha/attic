Ext.define('Sales.store.QuotationImport', {
	extend: 'Ext.data.Store',
	storeId: 'QuotationImport',
	fields: [ 'id', 'status', 'customer', 'note', 'modified', 'created' ],
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
