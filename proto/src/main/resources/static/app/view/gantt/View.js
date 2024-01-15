Ext.define('Proto.view.gantt.View', {
	extend: 'Ext.panel.Panel',
	requires: [ 'Proto.view.gantt.Controller', 'Proto.view.gantt.ViewModel' ],
	title: 'Gantt',
	closable: true,
	controller: {
		xclass: 'Proto.view.gantt.Controller'
	},

	viewModel: {
		xclass: 'Proto.view.gantt.ViewModel'
	},
	
	layout: 'fit',
	
	items: []

});