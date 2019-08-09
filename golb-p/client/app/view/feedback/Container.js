Ext.define('Golb.view.feedback.Container', {
	extend: 'Ext.container.Container',

	layout: {
		type: 'fit'
	},

	controller: {
		xclass: 'Golb.view.feedback.Controller'
	},

	viewModel: {
		xclass: 'Golb.view.feedback.ViewModel'
	},

	items: [ {
		xclass: 'Golb.view.feedback.Grid'
	} ]
});