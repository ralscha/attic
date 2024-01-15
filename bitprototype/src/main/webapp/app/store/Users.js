Ext.define('BitP.store.Users', {
	extend: 'Ext.data.Store',
	model: 'BitP.model.User',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	autoSync: false,
	sorters: [ {
		property: 'name',
		direction: 'ASC'
	} ]
});