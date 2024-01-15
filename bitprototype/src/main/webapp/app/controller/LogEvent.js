Ext.define('BitP.controller.LogEvent', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			removed: 'onRemoved'
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

	init: function() {
		this.doGridRefresh();
	},

	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
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
		logEventService.deleteAll(filter && filter.value, function() {
			BitP.ux.window.Notification.info(i18n.successful, i18n.logevents_deleted);
			this.doGridRefresh();
		}, this);
	},

	/* <debug> */
	addTestData: function() {
		logEventService.addTestData(function() {
			BitP.ux.window.Notification.info(i18n.successful, i18n.logevents_testinserted);
			this.doGridRefresh();
		}, this);

	},
	/* </debug> */

	doGridRefresh: function() {
		this.getView().getStore().load();
	}

});