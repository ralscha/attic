Ext.define('Proto.view.usersettings.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Proto.model.PersistentLogin' ],

	stores: {
		persistentLogins: {
			model: 'Proto.model.PersistentLogin',
			autoLoad: false,
			remoteSort: false,
			remoteFilter: false,	
			pageSize: 0,
			sorters: [ {
				property: 'lastUsed',
				direction: 'DESC'
			} ],
			proxy: {
				type: 'direct',
				directFn: 'securityService.readPersistentLogins'
			}			
		}
	}

});