Ext.define('Starter.view.usersettings.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Starter.model.AccessLog', 'Starter.model.PersistentLogin' ],

	stores: {
		last10logs: {
			model: 'Starter.model.AccessLog',
			autoLoad: true,
			remoteSort: false,
			remoteFilter: false,
			pageSize: 0,
			sorters: [ {
				property: 'loginTimestamp',
				direction: 'DESC'
			} ],
			proxy: {
				type: 'direct',
				directFn: 'accessLogService.last10Logs'
			}
		},
		persistentLogins: {
			model: 'Starter.model.PersistentLogin',
			autoLoad: true,
			remoteSort: false,
			remoteFilter: false,
			pageSize: 0,
			sorters: [ {
				property: 'lastUsed',
				direction: 'DESC'
			} ],
			proxy: {
				type: 'direct',
				directFn: 'accessLogService.readPersistentLogins'
			}
		}
	}

});