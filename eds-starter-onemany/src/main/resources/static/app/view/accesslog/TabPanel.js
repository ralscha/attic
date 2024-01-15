Ext.define('Starter.view.accesslog.TabPanel', {
	extend: 'Ext.tab.Panel',
	requires: [ 'Starter.view.accesslog.Grid', 'Starter.view.accesslog.OsPanel', 'Starter.view.accesslog.UaPanel', 'Starter.view.accesslog.Controller', 'Starter.view.accesslog.ViewModel' ],

	controller: {
		xclass: 'Starter.view.accesslog.Controller'
	},

	viewModel: {
		xclass: 'Starter.view.accesslog.ViewModel'
	},
	icon: 'resources/images/data_scroll.png',
	title: i18n.navigation_security_accesslog,

	listeners: {
		tabchange: 'onTabChange'
	},

	items: [ {
		xclass: 'Starter.view.accesslog.Grid'
	}, {
		xclass: 'Starter.view.accesslog.UaPanel'
	}, {
		xclass: 'Starter.view.accesslog.OsPanel'
	} ]

});
