Ext.define('Proto.view.main.Main', {
	extend: 'Ext.container.Container',
	requires: [ 'Proto.view.main.MainController', 'Proto.view.main.MainModel', 'Proto.view.main.SideBar', 'Proto.view.main.Header' ],

	controller: {
		xclass: 'Proto.view.main.MainController'
	},

	viewModel: {
		xclass: 'Proto.view.main.MainModel'
	},

	layout: {
		type: 'border',
		padding: 3
	},

	items: [ {
		xclass: 'Proto.view.main.SideBar',
		region: 'west',
		reference: 'navigationTree',
		split: true
	}, {
		xclass: 'Proto.view.main.Header',
		region: 'north',
		split: false
	}, {
		xtype: 'tabpanel',
		region: 'center',
		reference: 'centerTabPanel',
		plain: true,
		split: true,
		plugins: [ 'tabreorderer', {
			ptype: 'tabclosemenu',
			closeTabText: i18n.tabclosemenu_close,
			closeOthersTabsText: i18n.tabclosemenu_closeother,
			closeAllTabsText: i18n.tabclosemenu_closeall
		} ],
		listeners: {
			tabchange: 'onTabChange',
			remove: 'onTabRemove'
		}
	} ]
});
