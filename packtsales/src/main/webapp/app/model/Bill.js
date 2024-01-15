Ext.define('Sales.model.Bill', {
	extend: 'Ext.data.Model',
	fields: [ 'id',
	'customer', 'addr', 'note', 'modified', 'created' ]
});
