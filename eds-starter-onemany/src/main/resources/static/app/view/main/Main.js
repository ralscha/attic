Ext.define('Starter.view.main.Main', {
	extend: 'Ext.container.Container',
	requires: [ 'Starter.view.main.MainController', 'Starter.view.main.MainModel', 'Starter.view.main.SideBar', 'Starter.view.main.Header' ],

	controller: {
		xclass: 'Starter.view.main.MainController'
	},

	viewModel: {
		xclass: 'Starter.view.main.MainModel'
	},

	layout: {
		type: 'border',
		padding: 5
	},

	items: [ {
		xclass: 'Starter.view.main.SideBar',
		region: 'west',
		reference: 'navigationTree',
		split: true
	}, {
		xclass: 'Starter.view.main.Header',
		region: 'north',
		split: false
	}, {
		xtype: 'container',
		region: 'center'
	} ]
});
