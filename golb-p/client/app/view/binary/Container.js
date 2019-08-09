Ext.define('Golb.view.binary.Container', {
	extend: 'Ext.container.Container',

	layout: {
		type: 'card'
	},

	controller: {
		xclass: 'Golb.view.binary.Controller'
	},

	viewModel: {
		xclass: 'Golb.view.binary.ViewModel'
	},

	items: [ {
		xclass: 'Golb.view.binary.Grid'
	} ]
});