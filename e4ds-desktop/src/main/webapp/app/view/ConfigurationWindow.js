Ext.define('E4desk.view.ConfigurationWindow', {
	extend: 'Ext.window.Window',
	requires: ['E4desk.view.ConfigurationEdit'],
	controller: 'E4desk.controller.Configuration',
	stateId: 'E4desk.view.ConfigurationWindow',

	title: i18n.configuration,
	width: 500,
	height: 480,
	iconCls: 'configuration-icon',

	layout: {
		type: 'fit'
	},

	initComponent: function() {
		this.items = Ext.create('E4desk.view.ConfigurationEdit', {
			itemId: 'configurationEditPanel'
		});		
		this.callParent(arguments);
	}

});
