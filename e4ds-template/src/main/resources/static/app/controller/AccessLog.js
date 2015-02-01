Ext.define('E4ds.controller.AccessLog', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			removed: 'onRemoved',
			tabchange: 'onTabChange'
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
		},
		grid: {
			selector: 'grid'
		},
		uaYearsCB: {
			change: 'onUaYearsCBChange'
		},
		osYearsCB: {
			change: 'onOsYearsCBChange'
		},
		uaChart: true,
		osChart: true
	},

	init: function() {
		this.doGridRefresh();
	},

	onTabChange: function(tabPanel, newCard) {
		if (newCard.itemId === 'uaGraphPanel') {
			var uaStore = this.getUaYearsCB().getStore();
			uaStore.load({
				scope: this,
				callback: function(records, operation, success) {
					if (records.length > 0) {
						this.getUaYearsCB().select(records[records.length - 1]);
					}
				}
			});
		}
		else if (newCard.itemId === 'osGraphPanel') {
			var osStore = this.getOsYearsCB().getStore();
			osStore.load({
				scope: this,
				callback: function(records, operation, success) {
					if (records.length > 0) {
						this.getOsYearsCB().select(records[records.length - 1]);
					}
				}
			});
		}
	},

	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
	},

	handleFilter: function(field, newValue) {
		var myStore = this.getGrid().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('filter', newValue);
		}
		else {
			myStore.clearFilter();
		}
	},

	deleteAll: function() {
		accessLogService.deleteAll(function() {
			E4ds.ux.window.Notification.info(i18n.successful, i18n.accesslog_deleted);
			this.doGridRefresh();
		}, this);
	},

	onUaYearsCBChange: function(cb, newValue) {
		this.getUaChart().getStore().load({
			params: {
				queryYear: newValue
			}
		});
	},

	onOsYearsCBChange: function(cb, newValue) {
		this.getOsChart().getStore().load({
			params: {
				queryYear: newValue
			}
		});
	},

	/* <debug> */
	addTestData: function() {
		accessLogService.addTestData(function() {
			E4ds.ux.window.Notification
					.info(i18n.successful, i18n.accesslog_testinserted);
			this.doGridRefresh();
		}, this);
	},
	/* </debug> */

	doGridRefresh: function() {
		this.getGrid().getStore().load();
	}

});