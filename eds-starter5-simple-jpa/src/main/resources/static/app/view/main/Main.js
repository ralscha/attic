Ext.define('SimpleApp.view.main.Main', {
	extend: 'Ext.container.Container',
	requires: [ 'SimpleApp.view.main.MainController', 'SimpleApp.view.crud.UserGrid',
			'SimpleApp.view.main.MainModel', 'SimpleApp.view.tree.TreePanel',
			'SimpleApp.view.poll.PollPanel', 'SimpleApp.view.form.FormPanel' ],

	controller: {
		xclass: 'SimpleApp.view.main.MainController'
	},
	viewModel: {
		xclass: 'SimpleApp.view.main.MainModel'
	},

	layout: {
		align: 'stretch',
		type: 'vbox'
	},

	items: [ {
		xtype: 'container',
		layout: {
			align: 'stretch',
			type: 'hbox'
		},
		flex: 1,
		items: [ {
			xclass: 'SimpleApp.view.crud.UserGrid',
			flex: 1,
			margins: 5
		}, {
			xclass: 'SimpleApp.view.poll.PollPanel',
			flex: 1,
			margins: 5
		} ]
	}, {
		xtype: 'container',
		layout: {
			align: 'stretch',
			type: 'hbox'
		},
		flex: 1,
		items: [ {
			xclass: 'SimpleApp.view.form.FormPanel',
			flex: 1,
			margins: 5
		}, {
			xclass: 'SimpleApp.view.tree.TreePanel',
			flex: 1,
			margins: 5
		} ]
	} ]

});
