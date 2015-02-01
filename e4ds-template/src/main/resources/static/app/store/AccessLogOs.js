Ext.define('E4ds.store.AccessLogOs', {
	extend: 'Ext.data.Store',

	autoLoad: false,
	remoteSort: false,
	remoteFilter: false,

	fields: [ {
		name: "name",
		type: "string"
	}, {
		name: 'value',
		type: 'float'
	} ],

	proxy: {
		type: 'direct',
		directFn: 'accessLogService.readOsStats',
		pageParam: undefined,
		startParam: undefined,
		limitParam: undefined
	}
});