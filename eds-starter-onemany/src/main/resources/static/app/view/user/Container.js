Ext.define('Starter.view.user.Container', {
	extend: 'Ext.container.Container',
	requires: [ 'Starter.view.user.Controller', 'Starter.view.user.ViewModel', 'Starter.view.user.Grid', 'Starter.view.user.Edit' ],

	layout: 'card',

	controller: {
		xclass: 'Starter.view.user.Controller'
	},

	viewModel: {
		xclass: 'Starter.view.user.ViewModel'
	},

	items: [ {
		xclass: 'Starter.view.user.Grid'
	}, {
		xclass: 'Starter.view.user.Edit'
	} ]
});