Ext.define('Changelog.view.configuration.Edit', {
	extend: 'Ext.form.Panel',
	controller: 'Changelog.controller.ConfigurationController',
	
	title: i18n.config,
	closable: true,
	trackResetOnLoad: true,
	
	fieldDefaults: {
		msgTarget: 'side'
	},

	bodyPadding: 10,

	initComponent: function() {
		var me = this;
		
		Ext.applyIf(me, {
			defaults: {
				labelWidth: 160
			},
			items: [{
				xtype: 'combobox',
				name: 'logLevel',
				fieldLabel: i18n.config_loglevel,
				store: Ext.create('Changelog.store.LogLevels'),
				valueField: 'level',
				displayField: 'level'
			}, {
				xtype: 'checkbox',
				name: 'buildEnabled',
				fieldLabel: i18n.config_buildenabled
			}, {
				xtype: 'htmleditor',
				name: 'infoTxt',
				width: 900,
				height: 300,
				fieldLabel: i18n.config_infotext
			}, {
				xtype: 'button',
				text: i18n.save,
				itemId: 'saveButton'
			}],
		});
		
		me.callParent(arguments);
	}

});