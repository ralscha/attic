Ext.define('Starter.view.config.Form', {
	extend: 'Ext.form.Panel',
	requires: [ 'Starter.view.config.Controller' ],

	controller: {
		xclass: 'Starter.view.config.Controller'
	},

	viewModel: {
		data: {
			testEmailButtonEnabled: false
		}
	},
	layout: 'vbox',

	title: i18n.navigation_system_config,
	closable: true,

	fieldDefaults: {
		msgTarget: 'qtip'
	},

	bodyPadding: 10,

	items: [ {
		xtype: 'fieldset',
		title: i18n.config_logging,
		collapsible: false,
		items: [ {
			xtype: 'combobox',
			fieldLabel: i18n.config_loglevel,
			name: 'logLevel',
			bind: {
				store: '{logLevels}'
			},
			valueField: 'level',
			displayField: 'level',
			queryMode: 'local',
			forceSelection: true,
			value: 'ERROR'
		} ]
	}, {
		xtype: 'fieldset',
		title: i18n.config_loginlock,
		collapsible: false,

		items: [ {
			xtype: 'fieldcontainer',
			layout: 'hbox',
			fieldLabel: i18n.config_loginlock_attempts,
			labelWidth: 155,
			items: [ {
				xtype: 'numberfield',
				hideLabel: true,
				fieldLabel: i18n.config_loginlock_attempts,
				name: 'loginLockAttempts',
				allowBlank: true,
				minValue: 0,
				maxValue: 999999,
				width: 55
			}, {
				xtype: 'displayfield',
				value: i18n.config_loginlock_attempts2,
				margin: '0 0 0 10'
			} ]
		}, {
			xtype: 'fieldcontainer',
			layout: 'hbox',
			fieldLabel: i18n.config_loginlock_minutes,
			labelWidth: 155,
			items: [ {
				xtype: 'numberfield',
				hideLabel: true,
				fieldLabel: i18n.config_loginlock_minutes,
				name: 'loginLockMinutes',
				allowBlank: true,
				minValue: 0,
				maxValue: 999999,
				width: 55
			}, {
				xtype: 'displayfield',
				value: i18n.config_loginlock_minutes2,
				margin: '0 0 0 10'
			} ]
		} ]
	}, {
		xtype: 'container',
		layout: 'hbox',
		items: [ {
			xtype: 'button',
			text: i18n.save,
			glyph: 0xe80d,
			formBind: true,
			handler: 'onSaveButtonClick',
			ui: 'default-toolbar'
		}, {
			xtype: 'tbseparator',
			width: 100
		}, {
			xtype: 'fieldcontainer',
			layout: 'hbox',
			width: 400,
			items: [ {
				xtype: 'textfield',
				vtype: 'email',
				bind: {
					value: '{testEmailReceiver}'
				},
				emptyText: i18n.config_testReceiver,
				listeners: {
					change: 'onTestEmailReceiverChange'
				}
			}, {
				xtype: 'button',
				text: i18n.config_sendTestEmail,
				glyph: 0xe800,
				bind: {
					disabled: '{!testEmailButtonEnabled}'
				},
				margin: '0 0 0 10',
				handler: 'onSendTestEmailClick',
				ui: 'default-toolbar'
			} ]
		} ]
	} ]
});