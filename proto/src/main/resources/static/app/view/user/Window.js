Ext.define('Proto.view.user.Window', {
	extend: 'Ext.window.Window',
	requires: [ 'Proto.view.user.WindowController', 'Proto.Util' ],
	stateId: 'Proto.view.user.Window',
	stateful: true,	
	
	layout: 'fit',
	width: 500,
	resizable: true,
	constrain: false,
	modal: true,
	autoShow: false,
	ghost: false,
	glyph: 0xe803,
	defaultFocus: 'loginName',

	controller: {
		xclass: 'Proto.view.user.WindowController'
	},

	items: [ {
		xtype: 'form',
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
			itemId: 'loginName',
			name: 'loginName',
			fieldLabel: i18n.user_loginname,
			allowBlank: false
		}, {
			name: 'firstName',
			fieldLabel: i18n.user_firstname,
			allowBlank: false
		}, {
			name: 'lastName',
			fieldLabel: i18n.user_lastname,
			allowBlank: false
		}, {
			name: 'email',
			fieldLabel: i18n.user_email,
			vtype: 'email',
			allowBlank: false
		}, {
			xtype: 'combobox',
			fieldLabel: i18n.user_language,
			name: 'locale',
			store: 'languages',
			valueField: 'value',
			queryMode: 'local',
			emptyText: i18n.user_selectlanguage,
			allowBlank: false,
			forceSelection: true,
			editable: false
		}, {
			fieldLabel: i18n.user_enabled,
			name: 'enabled',
			xtype: 'checkboxfield',
			inputValue: 'true',
			uncheckedValue: 'false'
		}, {
			xtype: 'combobox',
			fieldLabel: i18n.user_roles,
			store: 'roles',
			name: 'role',
			displayField: 'name',
			valueField: 'name',
			queryMode: 'local',
			forceSelection: true,
			editable: false
		}, {
			xtype: 'checkbox',
			name: 'passwordReset',
			inputValue: 'true',
			uncheckedValue: 'false',
			fieldLabel: i18n.user_passwordreset
		} ],

		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'bottom',
			items: [ '->', {
				xtype: 'button',
				text: Proto.Util.underline(i18n.save, 'S'),
				accessKey: 's',
				glyph: 0xe80d,
				formBind: true,
				handler: 'save'
			}, {
				xtype: 'button',
				text: Proto.Util.underline(i18n.close, 'C'),
				accessKey: 'c',
				glyph: 0xe80e,
				handler: 'close'
			} ]
		} ]

	} ]

});