Ext.define('E4desk.store.AccessLogs', {
	extend: 'Ext.data.Store',
	model: 'E4desk.model.AccessLog',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 20,
	sorters: [ {
		property: 'logIn',
		direction: 'DESC'
	} ]
});