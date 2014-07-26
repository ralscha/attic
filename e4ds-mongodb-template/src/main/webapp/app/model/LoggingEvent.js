Ext.define('E4ds.model.LoggingEvent', {
	extend: 'Ext.data.Model',
	fields: [ {
		name: 'timeStamp',
		type: 'date',
		dateFormat: 'c'
	}, 'message', 'formattedMessage', 'level', 'caller', 'ip', 'userName', 'stacktrace' ],

	proxy: {
		type: 'direct',
		directFn: loggingEventService.load,
		reader: {
			root: 'records'
		}
	}
});