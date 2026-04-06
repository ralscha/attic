Ext.define('MusicSearch.Application', {
	extend: 'Ext.app.Application',
	requires: [ 'Ext.direct.*', 'Ext.plugin.Viewport' ],
	name: 'MusicSearch',

    quickTips: false,
    platformConfig: {
        desktop: {
            quickTips: true
        }
    },

	constructor() {
		soundManager.setup({
			url: serverUrl + 'resources/swf/'
		});

		REMOTING_API.url = serverUrl + REMOTING_API.url;
		REMOTING_API.maxRetries = 0;
		Ext.direct.Manager.addProvider(REMOTING_API);

		this.callParent();
	},

	onAppUpdate() {
		window.location.reload();
	}
});
