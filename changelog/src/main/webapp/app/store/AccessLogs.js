Ext.define('Changelog.store.AccessLogs', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.AccessLog',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'logIn',
		direction: 'DESC'
	} ]
});