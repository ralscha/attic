Ext.define('E4ds.model.AccessLog', {
	extend: 'Ext.data.Model',
	fields: [ {
		name: 'id',
		type: 'string'
	}, {
		name: 'sessionId',
		type: 'string'
	}, {
		name: 'userName',
		type: 'string'
	}, {
		name: 'logIn',
		type: 'date',
		dateFormat: 'c'
	}, {
		name: 'logOut',
		type: 'date',
		dateFormat: 'c'
	}, {
		name: 'durationInSeconds',
		type: 'string'
	}],

	proxy: {
		type: 'direct',
		directFn: accessLogService.load,
		reader: {
			root: 'records'
		}
	}
});