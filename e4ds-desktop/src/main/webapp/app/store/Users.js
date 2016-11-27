Ext.define('E4desk.store.Users', {
	extend: 'Ext.data.Store',
	model: 'E4desk.model.User',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 20,
	autoSync: false,
	sorters: [ {
		property: 'name',
		direction: 'ASC'
	} ]
});