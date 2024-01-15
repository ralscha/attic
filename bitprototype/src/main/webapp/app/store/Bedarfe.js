Ext.define('BitP.store.Bedarfe', {
	extend: 'Ext.data.Store',
	model: 'BitP.model.Bedarf',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	autoSync: false,
	sorters: [ {
		property: 'id',
		direction: 'ASC'
	} ]
});