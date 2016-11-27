Ext.define('E4desk.controller.LoggingEvent', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			show: 'onShow'
		},
		grid: true,
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
		var myStore = this.getGrid().getStore();
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
		var filter = this.getGrid().getStore().filters.get(0);
		loggingEventService.deleteAll(filter && filter.value, function() {
			E4desk.ux.window.Notification.info(i18n.successful, i18n.logevents_deleted);
			this.doGridRefresh();
		}, this);
	},

	addTestData: function() {
		loggingEventService.addTestData(function() {
			E4desk.ux.window.Notification.info(i18n.successful, i18n.logevents_testinserted);
			this.doGridRefresh();
		}, this);

	},

	onShow: function() {
		this.doGridRefresh();
	},

	doGridRefresh: function() {
		this.getGrid().getStore().load();
	}

});