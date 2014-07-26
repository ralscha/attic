Ext.define('E4ds.view.config.Edit', {
	extend: 'Ext.form.Panel',
	alias: 'widget.configedit',

	title: i18n.config,
	closable: true,

	fieldDefaults: {
		msgTarget: 'side'
	},

	bodyPadding: 5,

	initComponent: function() {
		var me = this;
		
		me.items = [ {
			xtype: 'combobox',
			fieldLabel: i18n.config_loglevel,
			name: 'logLevel',
			labelWidth: 110,
			store: 'LogLevels',
			valueField: 'level',
			displayField: 'level',
			queryMode: 'local',
			forceSelection: true,
			value: 'ERROR'
		} ];

		me.callParent(arguments);
	}

});