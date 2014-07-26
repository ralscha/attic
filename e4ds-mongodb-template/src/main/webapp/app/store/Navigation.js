Ext.define('E4ds.store.Navigation', {
	extend: 'Ext.data.TreeStore',
	autoLoad: true,	
    nodeParam: 'id',
    proxy: {
        type: 'direct',
        directFn: navigationService.getNavigation
    }	

});
