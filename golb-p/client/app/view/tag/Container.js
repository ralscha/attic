Ext.define('Golb.view.tag.Container', {
	extend: 'Ext.container.Container',

	layout: {
		type: 'card'
	},

	controller: {
		xclass: 'Golb.view.tag.Controller'
	},

	viewModel: {
		xclass: 'Golb.view.tag.ViewModel'
	},

	items: [ {
		xclass: 'Golb.view.tag.Grid'
	} ]
});