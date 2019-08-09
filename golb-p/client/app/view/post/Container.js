Ext.define('Golb.view.post.Container', {
	extend: 'Ext.container.Container',

	layout: {
		type: 'card'
	},

	controller: {
		xclass: 'Golb.view.post.Controller'
	},

	viewModel: {
		xclass: 'Golb.view.post.ViewModel'
	},

	items: [ {
		xclass: 'Golb.view.post.Grid'
	} ]
});