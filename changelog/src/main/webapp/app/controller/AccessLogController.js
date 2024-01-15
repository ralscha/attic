Ext.define('Changelog.controller.AccessLogController', {
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
		filterField: {
			filter: 'handleFilter'
		}		
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
			Changelog.ux.window.Notification.info(i18n.successful, i18n.accesslog_deleted);
			this.doGridRefresh();
		}, this);		
	},

	addTestData: function() {
		accessLogService.addTestData(function() {
			Changelog.ux.window.Notification.info(i18n.successful, i18n.accesslog_testinserted);
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