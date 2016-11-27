Ext.define('Starter.view.accesslog.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Starter.model.AccessLog' ],

	stores: {
		accessLogs: {
			model: 'Starter.model.AccessLog',
			autoLoad: true,
			remoteSort: true,
			remoteFilter: true,
			pageSize: 30,
			sorters: [ {
				property: 'loginTimestamp',
				direction: 'DESC'
			} ]
		},
		accessLogYears: {
			autoLoad: true,
			remoteSort: false,
			remoteFilter: false,
			pageSize: 0,

			fields: [ {
				name: 'year',
				type: 'int'
			} ],

			sorters: [ {
				property: 'year',
				direction: 'DESC'
			} ],

			proxy: {
				type: 'direct',
				directFn: 'accessLogService.readAccessLogYears'
			}
		}
	}

});