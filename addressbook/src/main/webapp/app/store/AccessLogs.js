Ext.define('Ab.store.AccessLogs', {
	extend: 'Ext.data.Store',
	model: 'Ab.model.AccessLog',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'logIn',
		direction: 'DESC'
	} ]
});