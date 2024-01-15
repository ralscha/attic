Ext.define('Ab.store.LogEvents', {
	extend: 'Ext.data.Store',
	model: 'Ab.model.LogEvent',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'eventDate',
		direction: 'DESC'
	} ]
});