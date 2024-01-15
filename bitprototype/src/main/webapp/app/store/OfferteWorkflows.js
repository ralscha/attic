Ext.define('BitP.store.OfferteWorkflows', {
	extend: 'Ext.data.Store',
	model: 'BitP.model.OfferteWorkflow',
	autoLoad: false,
	remoteSort: false,
	remoteFilter: false,
	autoSync: false,
	sorters: [ {
		property: 'created',
		direction: 'ASC'
	} ]
});