Ext.define('Addressbook.store.InfoTypes', {
	extend: 'Ext.data.ArrayStore',
	fields: [ 'type' ],
	data: [ [ 'Home' ], [ 'Work' ], [ 'Mobile' ] ]
});
