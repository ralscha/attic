Ext.define('Changelog.store.LoggingEvents', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.LoggingEvent',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'dateTime',
		direction: 'DESC'
	} ]
});