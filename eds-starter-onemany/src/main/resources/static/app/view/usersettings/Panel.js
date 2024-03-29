Ext.define('Starter.view.usersettings.Panel', {
	extend: 'Ext.tab.Panel',
	requires: [ 'Starter.view.usersettings.Controller', 'Starter.view.usersettings.ViewModel' ],

	controller: {
		xclass: 'Starter.view.usersettings.Controller'
	},

	viewModel: {
		xclass: 'Starter.view.usersettings.ViewModel'
	},

	title: i18n.user_settings,
	layout: 'fit',
	glyph: 0xe803,
	defaultFocus: 'textfield[name=firstName]',

	items: [ {
		xtype: 'form',
		title: i18n.user_personal,
		reference: 'userSettingsForm',
		padding: 5,
		bodyPadding: 10,

		defaultType: 'textfield',
		defaults: {
			anchor: '100%'
		},

		fieldDefaults: {
			msgTarget: 'qtip'
		},

		items: [ {
			itemId: 'firstName',
			bind: '{user.firstName}',
			name: 'firstName',
			fieldLabel: i18n.user_firstname,
			allowBlank: false
		}, {
			bind: '{user.lastName}',
			name: 'lastName',
			fieldLabel: i18n.user_lastname,
			allowBlank: false
		}, {
			bind: '{user.email}',
			name: 'email',
			fieldLabel: i18n.user_email,
			vtype: 'email',
			allowBlank: false
		}, {
			xtype: 'combobox',
			fieldLabel: i18n.user_language,
			bind: {
				value: '{user.locale}'
			},
			store: 'languages',
			valueField: 'value',
			queryMode: 'local',
			emptyText: i18n.user_selectlanguage,
			allowBlank: false,
			name: 'locale',
			forceSelection: true,
			editable: false
		}, {
			xtype: 'label',
			html: '<hr />'
		}, {
			bind: '{user.currentPassword}',
			name: 'currentPassword',
			fieldLabel: i18n.user_currentpassword,
			inputType: 'password'
		}, {
			bind: '{user.newPassword}',
			name: 'newPassword',
			fieldLabel: i18n.user_newpassword,
			inputType: 'password',
			validator: function() {
				var newPasswordRetypeField = this.up().getForm().findField('newPasswordRetype');
				var newPasswordRetype = newPasswordRetypeField.getValue();
				var newPassword = this.getValue();
				if ((Ext.isEmpty(newPassword) && Ext.isEmpty(newPasswordRetype)) || (newPassword === newPasswordRetype)) {
					newPasswordRetypeField.clearInvalid();
					return true;
				}
				newPasswordRetypeField.markInvalid(i18n.user_pwdonotmatch);
				return i18n.user_pwdonotmatch;
			}
		}, {
			bind: '{user.newPasswordRetype}',
			name: 'newPasswordRetype',
			fieldLabel: i18n.user_newpasswordretype,
			inputType: 'password',
			validator: function() {
				var newPasswordField = this.up().getForm().findField('newPassword');
				var newPassword = newPasswordField.getValue();
				var newPasswordRetype = this.getValue();
				if ((Ext.isEmpty(newPassword) && Ext.isEmpty(newPasswordRetype)) || (newPassword === newPasswordRetype)) {
					newPasswordField.clearInvalid();
					return true;
				}
				newPasswordField.markInvalid(i18n.user_pwdonotmatch);
				return i18n.user_pwdonotmatch;
			}
		} ],

		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ '->', {
				xtype: 'button',
				text: i18n.save,
				glyph: 0xe80d,
				formBind: true,
				handler: 'save'
			} ]
		} ]

	}, {
		xtype: 'grid',
		title: i18n.user_persistentlogins,
		bind: '{persistentLogins}',

		columns: [ {
			text: i18n.accesslog_ip,
			dataIndex: 'ipAddress',
			flex: 1
		}, {
			text: i18n.accesslog_location,
			dataIndex: 'location',
			flex: 1
		}, {
			text: i18n.accesslog_userAgent,
			dataIndex: 'userAgentName',
			flex: 1
		}, {
			text: i18n.accesslog_userAgentVersion,
			dataIndex: 'userAgentVersion',
			width: 100
		}, {
			text: i18n.accesslog_operatingSystem,
			dataIndex: 'operatingSystem',
			width: 200
		}, {
			text: i18n.user_persistentlogins_lastUsed,
			dataIndex: 'lastUsed',
			width: 170,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
		}, {
			xtype: 'actioncolumn',
			sortable: false,
			hideable: false,
			width: 30,
			items: [ {
				icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAaElEQVR42mNgoBVQVVU9AMQfoPgAOQZ8BGItKP5IrKYTQPyfAD6BzwAnIM4ggJ3wGXCGCBecwWcASMF5GBtZHEqfRxbHZcAhPAYcGjWATga8A2IfdAOgYu8IGRAMxL/wJCKQXDBVcy0Axpl8j7t7oPEAAAAASUVORK5CYII=',
				tooltip: i18n.destroy,
				handler: 'destroyPersistentLogin'
			} ]
		} ]
	}, {
		xtype: 'grid',
		title: i18n.user_lastlogins,
		bind: '{last10logs}',

		columns: [ {
			text: i18n.accesslog_ip,
			dataIndex: 'ipAddress',
			flex: 1
		}, {
			text: i18n.accesslog_location,
			dataIndex: 'location',
			flex: 1
		}, {
			text: i18n.accesslog_userAgent,
			dataIndex: 'userAgentName',
			flex: 1
		}, {
			text: i18n.accesslog_userAgentVersion,
			dataIndex: 'userAgentVersion',
			width: 100
		}, {
			text: i18n.accesslog_operatingSystem,
			dataIndex: 'operatingSystem',
			width: 200
		}, {
			text: i18n.accesslog_login,
			dataIndex: 'loginTimestamp',
			width: 170,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
		} ]
	}, {
		xtype: 'panel',
		title: i18n.user_settings_clear_state_title,
		bodyPadding: 20,
		layout: {
			type: 'vbox'
		},
		items: [ {
			xtype: 'button',
			handler: 'clearState',
			ui: 'default-toolbar',
			text: i18n.user_settings_clear_state
		} ]
	} ]

});
