Ext.define('SimpleApp.store.Companies', {
	extend: 'Ext.data.TreeStore',

	root: {
		expanded: true
	},

	proxy: {
		type: 'direct',
		directFn: 'treeLoadService.getTree'
	}
});