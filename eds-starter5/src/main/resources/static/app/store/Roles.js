Ext.define('Starter.store.Roles', {
	extend: 'Ext.data.Store',
	model: 'Starter.model.Role',
	storeId: 'roles',
	autoLoad: true,
	remoteFilter: false,
	remoteSort: false,
	pageSize: 0
});