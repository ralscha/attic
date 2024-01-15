Ext.define('BitP.controller.AccessLog', {
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
		filterField: {
			filter: 'handleFilter'
		}
	},

	init: function() {
		this.doGridRefresh();
	},

	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
	},

	handleFilter: function(field, newValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('filter', newValue);
		} else {
			myStore.clearFilter();
		}
	},

	deleteAll: function() {
		accessLogService.deleteAll(function() {
			BitP.ux.window.Notification.info(i18n.successful, i18n.accesslog_deleted);
			this.doGridRefresh();
		}, this);
	},

	/* <debug> */
	addTestData: function() {
		accessLogService.addTestData(function() {
			BitP.ux.window.Notification.info(i18n.successful, i18n.accesslog_testinserted);
			this.doGridRefresh();
		}, this);
	},
	/* </debug> */

	doGridRefresh: function() {
		this.getView().getStore().load();
	}

});