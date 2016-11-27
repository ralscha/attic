Ext.define('E4desk.store.LoggingEvents', {
	extend: 'Ext.data.Store',
	model: 'E4desk.model.LoggingEvent',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 20,
	sorters: [ {
		property: 'dateTime',
		direction: 'DESC'
	} ]
});