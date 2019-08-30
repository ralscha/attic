Ext.define('Starter.Application', {
	extend: 'Ext.app.Application',
	requires: [ 'Ext.plugin.Viewport', 'Ext.direct.*', 'Ext.form.action.DirectLoad', 'Ext.form.action.DirectSubmit', 'Ext.window.Toast' ],
	name: 'Starter',

	controllers: [ 'Root' ],

	stores: [ 'Companies', 'Departments' ],

	models: [ 'PageHit', 'User' ],

    quickTips: false,
    platformConfig: {
        desktop: {
            quickTips: true
        }
    },

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

	onAppUpdate() {
		window.location.reload();
	}
});
