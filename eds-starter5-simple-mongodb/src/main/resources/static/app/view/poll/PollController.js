Ext.define('SimpleApp.view.poll.PollController', {
	extend: 'Ext.app.ViewController',

	onData: function(provider, event) {
		if (event.data) {
			var store = this.getStore('pagehits');

			store.removeAll(true);

			Ext.each(Ext.Date.monthNames, function(name, ix) {
				store.add(new SimpleApp.model.PageHit({
					month: name.substring(0, 3),
					hit: event.data[ix]
				}));
			});

		}
	},

	startPolling: function() {
		var provider = Ext.direct.Manager.getProvider('chartDataPoller');
		this.getViewModel().set('running', true);

		if (!provider.isConnected()) {
			provider.addListener('data', this.onData, this);
			provider.connect();
		}
	},

	stopPolling: function() {
		var provider = Ext.direct.Manager.getProvider('chartDataPoller');
		this.getViewModel().set('running', false);

		if (provider.isConnected()) {
			provider.removeListener('data', this.onData);
			provider.disconnect();
		}
	}

});
