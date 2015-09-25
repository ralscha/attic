Ext.define('${jsAppNamespace}.view.main.Main', {
	extend: 'Ext.container.Container',
	requires: [ '${jsAppNamespace}.view.main.MainController', '${jsAppNamespace}.view.crud.UserGrid',
			'${jsAppNamespace}.view.main.MainModel', '${jsAppNamespace}.view.tree.TreePanel',
			'${jsAppNamespace}.view.poll.PollPanel', '${jsAppNamespace}.view.form.FormPanel' ],

	controller: {
		xclass: '${jsAppNamespace}.view.main.MainController'
	},
	viewModel: {
		xclass: '${jsAppNamespace}.view.main.MainModel'
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
			xclass: '${jsAppNamespace}.view.crud.UserGrid',
			flex: 1,
			margins: 5
		}, {
			xclass: '${jsAppNamespace}.view.poll.PollPanel',
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
			xclass: '${jsAppNamespace}.view.form.FormPanel',
			flex: 1,
			margins: 5
		}, {
			xclass: '${jsAppNamespace}.view.tree.TreePanel',
			flex: 1,
			margins: 5
		} ]
	} ]

});
