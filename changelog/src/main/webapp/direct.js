var heartbeat = new Ext.direct.PollingProvider({
	type: 'polling',
	interval: 5*60*1000, //5 minutes
	url: Ext.app.POLLING_URLS.heartbeat
});
Ext.direct.Manager.addProvider(Ext.app.REMOTING_API, heartbeat);	
