Ext.define('E4ds.controller.LoggingEvents', {
	extend: 'Ext.app.Controller',

	views: [ 'loggingevent.List' ],
	stores: [ 'LoggingEvents', 'LogLevels' ],
	models: [ 'LoggingEvent' ],

	refs: [ {
		ref: 'pagingtoolbar',
		selector: 'loggingeventlist pagingtoolbar'
	}, {
		ref: 'exportButton',
		selector: 'loggingeventlist button[action=export]'
	} ],

	init: function() {
		this.control({
			'loggingeventlist': {
				add: this.onAdd
			},
			'loggingeventlist button[action=deleteall]': {
				click: this.deleteAll
			},
			'loggingeventlist button[action=test]': {
				click: this.addTestData
			},
			'loggingeventlist combobox[name=logLevelFilter]': {
				change: this.filterLogLevel
			}
		});
	},

	filterLogLevel: function(field, newValue, oldValue) {
		var myStore = this.getLoggingEventsStore();
		if (newValue) {
			myStore.remoteFilter = false;
			myStore.clearFilter(true);
			myStore.remoteFilter = true;
			myStore.filter('level', newValue);
			this.getExportButton().setParams({
				level: newValue
			});
		} else {
			myStore.clearFilter();
			this.getExportButton().setParams();
		}
	},

	deleteAll: function() {
		var filter = this.getLoggingEventsStore().filters.get(0);
		loggingEventService.deleteAll(filter && filter.value, function() {
			Ext.ux.window.Notification.info(i18n.successful, i18n.logevents_deleted);
			this.doGridRefresh();
		}, this);		
	},

	addTestData: function() {
		loggingEventService.addTestData(function() {
			Ext.ux.window.Notification.info(i18n.successful, i18n.logevents_testinserted);
			this.doGridRefresh();	
		}, this);
		
	},

	onAdd: function(cmp, options) {
			this.getLoggingEventsStore().clearFilter();
	},

	doGridRefresh: function() {
		this.getPagingtoolbar().doRefresh();
	}

});