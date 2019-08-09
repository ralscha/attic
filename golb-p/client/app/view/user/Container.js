Ext.define('Golb.view.user.Container', {
	extend: 'Ext.container.Container',

	layout: {
		type: 'card'
	},

	controller: {
		xclass: 'Golb.view.user.Controller'
	},

	viewModel: {
		xclass: 'Golb.view.user.ViewModel'
	},

	items: [ {
		xclass: 'Golb.view.user.Grid'
	} ]
});