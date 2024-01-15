Ext.define('Todo.store.Type', {
	extend: 'Ext.data.Store',
	storeId: 'types',
	data: [ {
		value: 'PRIVATE'
	}, {
		value: 'WORK'
	} ]
});
