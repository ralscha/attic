Ext.define('BitP.store.LogEvents', {
	extend: 'Ext.data.Store',
	model: 'BitP.model.LogEvent',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'eventDate',
		direction: 'DESC'
	} ]
});