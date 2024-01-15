Ext.define('Starter.view.loggingevent.Controller', {
	extend: 'Ext.app.ViewController',

	init: function() {
		new Ext.LoadMask({
			target: this.getView(),
			msg: i18n.loading,
			store: this.getStore('loggingEvents')
		});
	},

	filterLogLevel: function(cb, newValue) {
		var store = this.getStore('loggingEvents');
		if (newValue) {
			this.getViewModel().set('levelFilter', newValue);
			store.filter('level', newValue);
		}
		else {
			this.getViewModel().set('levelFilter', null);
			store.clearFilter();
		}
	},

	deleteAll: function() {
		this.getView().mask(i18n.saving);
		var filter = this.getView().getStore().getFilters().getAt(0);
		loggingEventService.deleteAll(filter && filter.getValue(), function() {
			Starter.Util.successToast(i18n.logevents_deleted);
			this.getStore('loggingEvents').load();
			this.getView().unmask();
		}, this);
	}

	/* <debug> */
	,
	addTestData: function() {
		loggingEventService.addTestData(function() {
			this.getStore('loggingEvents').load();
		}, this);
	}
/* </debug> */

});
