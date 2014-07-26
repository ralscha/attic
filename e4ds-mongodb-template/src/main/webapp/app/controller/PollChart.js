Ext.define('E4ds.controller.PollChart', {
	extend: 'Ext.app.Controller',

	stores: [ 'PollChart' ],
	models: [ 'PollChart' ],
	views: [ 'poll.PollChart' ],

	refs: [ {
		ref: 'startStopButton',
		selector: 'pollchart button[action=control]'
	} ],

	init: function() {
		this.control({
			'pollchart': {
				add: this.onAdd,
				destroy: this.stopPolling,
				beforeactivate: this.startPolling,
				beforedeactivate: this.stopPolling
			},
			'pollchart button[action=control]': {
				click: this.startOrStop
			}
		});
	},

	onData: function(provider, event) {
		var store = this.getPollChartStore(), 
		    model = this.getPollChartModel();

		if (store.getCount() > 10) {
				store.removeAt(0);
			}

		store.add(model.create(event.data));
	},

	onAdd: function(cmp) {
		this.provider = Ext.direct.Manager.getProvider('chartdatapoller');
		this.startPolling();
	},

	startOrStop: function() {
		if (!this.provider.isConnected()) {
			this.startPolling();
		} else {
			this.stopPolling();
		}
	},

	startPolling: function() {
		var button = this.getStartStopButton();
		if (button) {
			button.setText(i18n.chart_stop);
			button.setIconCls('icon-stop');
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
			button.setIconCls('icon-start');
		}

		if (this.provider.isConnected()) {
			this.provider.removeListener('data', this.onData);
		this.provider.disconnect();
	}
	}


});
