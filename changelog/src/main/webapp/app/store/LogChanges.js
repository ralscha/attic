Ext.define('Changelog.store.LogChanges', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.LogChange',
	autoLoad: false,
	remoteSort: false,
	sorters: [ {
		property: 'typ',
		direction: 'ASC'
	} ]
});