Ext.define('Changelog.store.LogCustomerBuilds', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.LogCustomerBuild',
	autoLoad: false,
	remoteSort: false,
	sorters: [ {
		property: 'versionDate',
		direction: 'DESC'
	} ]
});