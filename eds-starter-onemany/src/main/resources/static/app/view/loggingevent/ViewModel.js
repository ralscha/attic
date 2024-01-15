Ext.define('Starter.view.loggingevent.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Starter.model.LoggingEvent' ],

	stores: {
		loggingEvents: {
			model: 'Starter.model.LoggingEvent',
			autoLoad: true,
			remoteSort: true,
			remoteFilter: true,
			pageSize: 30,
			sorters: [ {
				property: 'dateTime',
				direction: 'DESC'
			} ]
		}
	}

});