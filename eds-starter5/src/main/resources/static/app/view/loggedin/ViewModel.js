Ext.define('Starter.view.loggedin.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Starter.model.AccessLog' ],

	stores: {
		loggedInUsers: {
			model: 'Starter.model.AccessLog',
			autoLoad: false,
			remoteSort: false,
			remoteFilter: false,
			pageSize: 0,
			sorters: [ {
				property: 'loginTimestamp',
				direction: 'DESC'
			} ],
			proxy: {
				type: 'direct',
				directFn: 'accessLogService.readLoggedInUsers'
			}
		}
	}

});