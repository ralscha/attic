Ext.define('BitP.store.BedarfWorkflows', {
	extend: 'Ext.data.Store',
	model: 'BitP.model.BedarfWorkflow',
	autoLoad: false,
	remoteSort: false,
	remoteFilter: false,
	autoSync: false,
	sorters: [ {
		property: 'created',
		direction: 'ASC'
	} ]
});