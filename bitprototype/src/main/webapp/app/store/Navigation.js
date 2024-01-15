Ext.define('BitP.store.Navigation', {
	extend: 'Ext.data.TreeStore',
	requires: [ 'Ext.data.proxy.Direct' ],
	autoLoad: true,
	nodeParam: 'id',
	proxy: {
		type: 'direct',
		directFn: 'navigationService.getNavigation'
	}
});
