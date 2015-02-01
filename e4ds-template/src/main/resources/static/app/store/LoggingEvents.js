Ext.define('E4ds.store.LoggingEvents', {
	extend: 'Ext.data.Store',
	model: 'E4ds.model.LoggingEvent',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'dateTime',
		direction: 'DESC'
	} ]
});