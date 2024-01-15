Ext.define('Proto.store.Roles', {
	extend: 'Ext.data.Store',
	model: 'Proto.model.Role',
	storeId: 'roles',
	autoLoad: true,
	remoteFilter: false,
	remoteSort: false,
	pageSize: 0
});