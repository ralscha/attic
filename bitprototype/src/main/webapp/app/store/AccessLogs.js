Ext.define('BitP.store.AccessLogs', {
	extend: 'Ext.data.Store',
	model: 'BitP.model.AccessLog',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'logIn',
		direction: 'DESC'
	} ]
});