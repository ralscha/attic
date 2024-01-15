Ext.define('Changelog.view.log.View', {
	extend: 'Ext.panel.Panel',
	controller: 'Changelog.controller.LogController',

	layout: {
		align: 'stretch',
		type: 'hbox'
	},
	
	title: i18n.log,

	closable: true,

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'container',
			layout: {
				align: 'stretch',
				type: 'vbox'
			},
			flex: 1,
			items: [ Ext.create('Changelog.view.log.Customers', {
				flex: 1
			}), {
				xtype: 'splitter',
				style: {
					'background-color': '#DFE8F6'
				}
			}, Ext.create('Changelog.view.log.CustomerBuilds', {
				flex: 1
			}) ]
		}, Ext.create('Changelog.view.log.Changes', {
			flex: 5
		}) ];

		me.callParent(arguments);
	}
});