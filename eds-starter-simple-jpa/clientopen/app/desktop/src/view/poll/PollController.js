Ext.define('Starter.view.poll.PollController', {
    extend: 'Ext.app.ViewController',

    onData(provider, event) {
        if (event.data) {
            const store = this.getStore('pagehits');

            store.removeAll(true);

            Ext.each(Ext.Date.monthNames, (name, ix) => {
                store.add(new Starter.model.PageHit({
                    month: name.substring(0, 3),
                    hit: event.data[ix]
                }));
            });

        }
    },

    startPolling() {
        const provider = Ext.direct.Manager.getProvider('chartDataPoller');
        this.getViewModel().set('running', true);

        if (!provider.isConnected()) {
            provider.addListener('data', this.onData, this);
            provider.connect();
        }
    },

    stopPolling() {
        const provider = Ext.direct.Manager.getProvider('chartDataPoller');
        this.getViewModel().set('running', false);

        if (provider.isConnected()) {
            provider.removeListener('data', this.onData);
            provider.disconnect();
        }
    }

});
