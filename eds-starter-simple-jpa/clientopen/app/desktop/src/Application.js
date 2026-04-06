Ext.define('Starter.Application', {
    extend: 'Ext.app.Application',
    name: 'Starter',
    requires: ['Ext.direct.*', 'Starter.*'],

    stores: [ 'Companies', 'Departments' ],

    models: [ 'PageHit', 'User' ],

    constructor() {
        const chartDataPoller = new Ext.direct.PollingProvider({
            id: 'chartDataPoller',
            type: 'polling',
            interval: 5 * 1000, // 5 seconds
            url: serverUrl + POLLING_URLS.chart
        });

        REMOTING_API.url = serverUrl + REMOTING_API.url;
        REMOTING_API.maxRetries = 0;

        Ext.direct.Manager.addProvider(REMOTING_API, chartDataPoller);
        Ext.direct.Manager.getProvider('chartDataPoller').disconnect();

        this.callParent(arguments);
    },

    removeSplash: function () {
        Ext.getBody().removeCls('launching');
        const elem = document.getElementById("splash");
        elem.parentNode.removeChild(elem)
    },

    launch: function () {
        this.removeSplash();
        Ext.Viewport.add([{xclass: 'Starter.view.main.Main'}])
    },

    onAppUpdate: function () {
        window.location.reload();
    }
});
