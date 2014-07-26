Ext.define('E4ds.controller.AccessLogs', {
	extend: 'Ext.app.Controller',

	views: [ 'accesslog.List' ],
	stores: [ 'AccessLogs' ],
	models: [ 'AccessLog' ],

	refs: [ {
		ref: 'pagingtoolbar',
		selector: 'accessloglist pagingtoolbar'
	}, {
		ref: 'accesslogList',
		selector: 'accessloglist'
	} ],

	init: function() {
		this.control({
			'accessloglist': {
				add: this.onAdd
			},
			'accessloglist button[action=deleteall]': {
				click: this.deleteAll
			},
			'accessloglist button[action=test]': {
				click: this.addTestData
			},
			'accessloglist textfield': {
				filter: this.handleFilter,
			}
		});
	},

	handleFilter: function(field, newValue) {
		var myStore = this.getAccessLogsStore();
		if (newValue) {
			myStore.remoteFilter = false;
			myStore.clearFilter(true);
			myStore.remoteFilter = true;
			myStore.filter('filter', newValue);		
		} else {
			myStore.clearFilter();				
		}
	},
	
	deleteAll: function() {
		accessLogService.deleteAll(function() {
			Ext.ux.window.Notification.info(i18n.successful, i18n.accesslogs_deleted);
			this.doGridRefresh();
		}, this);		
	},

	addTestData: function() {
		accessLogService.addTestData(function() {
			Ext.ux.window.Notification.info(i18n.successful, i18n.accesslogs_testinserted);
			this.doGridRefresh();	
		}, this);
		
	},

	onAdd: function(cmp, options) {
			this.getAccessLogsStore().clearFilter();
	},

	doGridRefresh: function() {
		this.getPagingtoolbar().doRefresh();
	}

});