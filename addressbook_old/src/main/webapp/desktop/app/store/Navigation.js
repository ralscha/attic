Ext.define('Addressbook.store.Navigation', {
	extend: 'Ext.data.TreeStore',
	requires: [ 'Ext.data.proxy.Direct' ],
	autoLoad: false,
	nodeParam: 'id',
	proxy: {
		type: 'direct',
		directFn: 'navigationService.getNavigation'
	}
});
