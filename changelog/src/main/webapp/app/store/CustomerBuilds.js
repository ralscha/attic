Ext.define('Changelog.store.CustomerBuilds', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.CustomerBuild',
	autoLoad: false,
	autoSync: true,
	remoteSort: false,
	sorters: [ {
		property: 'versionDate',
		direction: 'DESC'
	} ]
});