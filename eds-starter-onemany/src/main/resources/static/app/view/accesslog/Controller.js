Ext.define('Starter.view.accesslog.Controller', {
	extend: 'Ext.app.ViewController',

	init: function() {
		new Ext.LoadMask({
			target: this.getView(),
			msg: i18n.loading,
			store: this.getStore('accessLogs')
		});

		new Ext.LoadMask({
			target: this.getView(),
			msg: i18n.loading,
			store: this.getStore('accessLogYears')
		});
	},

	onUserFilterSpecialKey: function(tf, e) {
		if (e.getKey() === e.ENTER) {
			this.onUserFilter(tf);
		}
	},

	onUserFilter: function(tf, trigger) {
		if (trigger && trigger.id === 'clear') {
			tf.setValue('');
		}

		var value = tf.getValue();
		var store = this.getStore('accessLogs');
		if (value) {
			store.filter('filter', value);
		}
		else {
			store.clearFilter();
		}
	},

	deleteAll: function() {
		this.getView().mask(i18n.saving);
		accessLogService.deleteAll(function() {
			Starter.Util.successToast(i18n.accesslog_deleted);
			this.getStore('accessLogs').load();
			this.getStore('accessLogYears').load();
			this.getView().unmask();
		}, this);
	},

	onUaYearsCBChange: function(cb, newValue) {
		if (newValue) {
			accessLogService.readUserAgentsStats(newValue, function(result) {
				this.lookupReference('uabar').updateData(result);
			}, this);
		}
	},

	onOsYearsCBChange: function(cb, newValue) {
		if (newValue) {
			accessLogService.readOsStats(newValue, function(result) {
				this.lookupReference('ospie').updateData(result);
			}, this);
		}
	},

	onTabChange: function(tabPanel, newCard) {
		var accessLogYearsStore = this.getStore('accessLogYears');
		if (accessLogYearsStore.getCount() > 0) {
			if (newCard.xclass === 'Starter.view.accesslog.UaPanel') {
				Ext.Function.defer(function() {
					this.lookupReference('uaYearsCombobox').select(accessLogYearsStore.first());
				}, 1, this);
			}
			else if (newCard.xclass === 'Starter.view.accesslog.OsPanel') {
				Ext.Function.defer(function() {
					this.lookupReference('osYearsCombobox').select(accessLogYearsStore.first());
				}, 1, this);
			}
		}
	}

	/* <debug> */
	,
	addTestData: function() {
		accessLogService.addTestData(function() {
			this.getStore('accessLogs').load();
			this.getStore('accessLogYears').load();
		}, this);
	}
/* </debug> */

});
