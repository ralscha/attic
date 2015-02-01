Ext.define('E4ds.store.AccessLogUserAgents', {
	extend: 'Ext.data.Store',

	autoLoad: false,
	remoteSort: false,
	remoteFilter: false,

	fields: [ {
		name: "yearMonth",
		type: "string"
	}, {
		name: 'IE',
		type: 'float'
	}, {
		name: 'Chrome',
		type: 'float'
	}, {
		name: 'Firefox',
		type: 'float'
	}, {
		name: 'Safari',
		type: 'float'
	}, {
		name: 'Opera',
		type: 'float'
	}, {
		name: 'Other',
		type: 'float'
	} ],

	proxy: {
		type: 'direct',
		directFn: 'accessLogService.readUserAgentsStats',
		pageParam: undefined,
		startParam: undefined,
		limitParam: undefined
	}
});