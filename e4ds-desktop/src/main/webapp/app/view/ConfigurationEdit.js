Ext.define('E4desk.view.ConfigurationEdit', {
	extend: 'Ext.form.Panel',

	width: 400,

	api: {
		load: 'appConfigurationService.load',
		submit: 'appConfigurationService.submit'
	},
	paramsAsHash: true,

	defaultType: 'textfield',
	defaults: {
		anchor: '100%'
	},

	fieldDefaults: {
		msgTarget: 'side'
	},

	bodyPadding: 10,

	initComponent: function() {

		var me = this;

		me.items = [ {
			xtype: 'fieldset',
			title: i18n.configuration_logging,
			collapsible: false,
			items: [ {
				xtype: 'combobox',
				itemId: 'logLevelCombobox',
				fieldLabel: i18n.configuration_loglevel,
				name: 'logLevel',
				store: Ext.create('E4desk.store.LogLevels'),
				valueField: 'level',
				displayField: 'level',
				queryMode: 'local',
				forceSelection: true,
				value: 'ERROR'
			} ]
		}, {
			xtype: 'fieldset',
			title: i18n.configuration_smtp,
			collapsible: false,
			defaultType: 'textfield',
			layout: 'anchor',
			defaults: {
				anchor: '100%'
			},
			items: [ {
				fieldLabel: i18n.configuration_sender,
				name: 'sender',
				allowBlank: false,
				vtype: 'email',
				maxLength: 1024,
				enforceMaxLength: true
			}, {
				fieldLabel: i18n.configuration_server,
				name: 'server',
				allowBlank: false,
				maxLength: 1024,
				enforceMaxLength: true
			}, {
				xtype: 'numberfield',
				fieldLabel: i18n.configuration_port,
				name: 'port',
				allowBlank: false,
				minValue: 1,
				maxValue: 65535,
				anchor: '50%'
			}, {
				fieldLabel: i18n.configuration_username,
				name: 'username',
				allowBlank: true,
				maxLength: 1024,
				enforceMaxLength: true
			}, {
				fieldLabel: i18n.configuration_password,
				name: 'password',
				allowBlank: true,
				maxLength: 1024,
				enforceMaxLength: true
			}, {
				xtype: 'fieldcontainer',
				fieldLabel: i18n.configuration_testReceiver,
				layout:'column',
				items: [ {
					xtype: 'textfield',
					vtype: 'email',
					name: 'testEmailReceiver',
					itemId: 'testEmailReceiverTf',
					columnWidth: 0.6
				}, {
					xtype: 'button',
					text: i18n.configuration_sendTestEmail,
					itemId: 'sendTestEmailButton',
					disabled: true,
					columnWidth: 0.4,
					margin: '0 0 0 5'
				}]
			} ]
		} ];

		me.buttons = [ {
			xtype: 'button',
			itemId: 'saveButton',
			text: i18n.save,
			icon: app_context_path + '/resources/images/save.png',
			disabled: true,
			formBind: true
		}, {
			text: i18n.cancel,
			itemId: 'cancelButton',
			icon: app_context_path + '/resources/images/cancel.png'
		} ];

		me.callParent(arguments);

	}

});