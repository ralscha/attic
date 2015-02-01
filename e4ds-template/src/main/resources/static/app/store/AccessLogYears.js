Ext.define('E4ds.store.AccessLogYears', {
	extend: 'Ext.data.Store',

	autoLoad: false,
	remoteSort: false,
	remoteFilter: false,

	fields: [ {
		name: "year",
		type: "int"
	} ],

	sorters: [ {
		property: 'year',
		direction: 'DESC'
	} ],

	proxy: {
		type: 'direct',
		directFn: 'accessLogService.readAccessLogYears',
		pageParam: undefined,
		startParam: undefined,
		limitParam: undefined
	}
});