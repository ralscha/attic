Ext.define('E4ds.view.config.Edit', {
	extend: 'Ext.form.Panel',
	controller: 'E4ds.controller.Config',
	border: true,
	title: i18n.config,
	closable: true,

	fieldDefaults: {
		msgTarget: 'side'
	},

	bodyPadding: 10,

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'fieldset',
			title: i18n.config_logging,
			collapsible: false,
			items: [ {
				xtype: 'combobox',
				itemId: 'logLevelCombobox',
				fieldLabel: i18n.config_loglevel,
				name: 'logLevel',
				store: Ext.create('E4ds.store.LogLevels'),
				valueField: 'level',
				displayField: 'level',
				queryMode: 'local',
				forceSelection: true,
				value: 'ERROR'
			} ]
		}, {
			xtype: 'fieldset',
			title: i18n.config_smtp,
			collapsible: false,
			defaultType: 'textfield',
			defaults: {
				width: 500
			},
			items: [ {
				fieldLabel: i18n.config_sender,
				name: 'sender',
				allowBlank: false,
				vtype: 'email',
				maxLength: 1024,
				enforceMaxLength: true
			}, {
				fieldLabel: i18n.config_server,
				name: 'server',
				allowBlank: false,
				maxLength: 1024,
				enforceMaxLength: true
			}, {
				xtype: 'numberfield',
				fieldLabel: i18n.config_port,
				name: 'port',
				allowBlank: false,
				width: 200,
				minValue: 1,
				maxValue: 65535
			}, {
				fieldLabel: i18n.config_username,
				name: 'username',
				allowBlank: true,
				maxLength: 1024,
				enforceMaxLength: true
			}, {
				fieldLabel: i18n.config_password,
				name: 'password',
				allowBlank: true,
				maxLength: 1024,
				enforceMaxLength: true
			}, {
				xtype: 'fieldcontainer',
				fieldLabel: i18n.config_testReceiver,
				layout: 'column',
				items: [ {
					xtype: 'textfield',
					vtype: 'email',
					name: 'testEmailReceiver',
					itemId: 'testEmailReceiverTf',
					columnWidth: 0.6
				}, {
					xtype: 'button',
					text: i18n.config_sendTestEmail,
					itemId: 'sendTestEmailButton',
					glyph: 0xe800,
					disabled: true,
					columnWidth: 0.4,
					margin: '0 0 0 10'
				} ]
			} ]
		} ];

		me.buttons = [ {
			xtype: 'button',
			itemId: 'saveButton',
			text: i18n.save,
			glyph: 0xe80d,
			formBind: true
		} ];

		me.callParent(arguments);
	}

});