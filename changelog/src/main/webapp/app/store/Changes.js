Ext.define('Changelog.store.Changes', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.Change',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	autoSync : true,
	sorters: [ {
		property: 'implementationDate',
		direction: 'DESC'
	} ]
});