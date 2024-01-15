Ext.define('Changelog.store.Documents', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.Documents',
	autoLoad: false,
	autoSync: true,
	remoteSort: false,
	sorters: [ {
		property: 'date',
		direction: 'DESC'
	} ]
});