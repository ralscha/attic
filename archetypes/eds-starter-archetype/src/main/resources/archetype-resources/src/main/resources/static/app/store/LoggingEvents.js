Ext.define('${jsAppNamespace}.store.LoggingEvents', {
	extend: 'Ext.data.Store',
	model: '${jsAppNamespace}.model.LoggingEvent',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'dateTime',
		direction: 'DESC'
	} ]
});