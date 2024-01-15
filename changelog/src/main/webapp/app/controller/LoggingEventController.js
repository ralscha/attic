Ext.define('Changelog.controller.LoggingEventController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated'	
		},		
		deleteAllButton: {
			click: 'deleteAll'
		},
		/* <debug> */
		testButton: {
			click: 'addTestData'
		},
		/* </debug> */
		logLevelFilter: {
			change: 'filterLogLevel'
		},
		exportButton: true
	},
		
	filterLogLevel: function(field, newValue, oldValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
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
		var filter = this.getView().getStore().filters.get(0);
		loggingEventService.deleteAll(filter && filter.value, function() {
			Changelog.ux.window.Notification.info(i18n.successful, i18n.logevents_deleted);
			this.doGridRefresh();
		}, this);		
	},

	addTestData: function() {
		loggingEventService.addTestData(function() {
			Changelog.ux.window.Notification.info(i18n.successful, i18n.logevents_testinserted);
			this.doGridRefresh();	
		}, this);
		
	},

	onActivated: function() {
	  this.doGridRefresh();
	},

	doGridRefresh: function() {
		this.getView().getStore().load();
	}

});