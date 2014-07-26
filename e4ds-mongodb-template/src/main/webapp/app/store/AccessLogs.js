Ext.define('E4ds.store.AccessLogs', {
	extend: 'Ext.data.Store',
	model: 'E4ds.model.AccessLog',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'logIn',
		direction: 'DESC'
	} ]
});