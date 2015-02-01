Ext.define('E4ds.controller.PollChart', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			removed: 'onRemoved'
		},
		startStopButton: {
			click: 'onStartStopClick'
		}
	},

	onData: function(provider, event) {
		var store = this.getView().store;

		if (store.getCount() > 10) {
			store.removeAt(0);
		}

		store.add(Ext.create('E4ds.model.PollChart', event.data));
	},

	init: function(cmp) {
		this.provider = Ext.direct.Manager.getProvider('chartdatapoller');
		this.startPolling();
	},

	onRemoved: function() {
		this.stopPolling();
		History.pushState({}, i18n.app_title, "?");
	},

	onStartStopClick: function() {
		if (!this.provider.isConnected()) {
			this.startPolling();
		}
		else {
			this.stopPolling();
		}
	},

	startPolling: function() {
		var button = this.getStartStopButton();
		if (button) {
			button.setText(i18n.chart_stop);
			button.setGlyph(0xe805);
		}

		if (!this.provider.isConnected()) {
			this.provider.addListener('data', this.onData, this);
			this.provider.connect();
		}
	},

	stopPolling: function() {
		var button = this.getStartStopButton();
		if (button) {
			button.setText(i18n.chart_start);
			button.setGlyph(0xe815);
		}

		if (this.provider.isConnected()) {
			this.provider.removeListener('data', this.onData);
			this.provider.disconnect();
		}
	}

});
