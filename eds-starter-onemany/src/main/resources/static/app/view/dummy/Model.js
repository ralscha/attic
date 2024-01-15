Ext.define('Starter.view.dummy.Model', {
	extend: 'Ext.data.Model',

	fields: [ 'name', 'price', 'revenue', 'growth', 'product', 'market' ],
	proxy: {
		type: 'direct',
		directFn: 'dummyService.read'
	}
});
