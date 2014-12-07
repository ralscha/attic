Ext.define('Packt.store.security.Groups', {
	extend: 'Ext.data.Store',
	requires: [ 'Packt.model.security.Group' ],
	model: 'Packt.model.security.Group',
	storeId: 'groups'
});