Ext.define('E4desk.view.UserEdit', {
	extend: 'Ext.form.Panel',
	requires: [ 'Ext.ux.form.MultiSelect', 'E4desk.store.Roles' ],
	width: 400,
	
	defaultType: 'textfield',
	defaults: {
		anchor: '100%'
	},

	api: {
		submit: 'userService.userFormPost'
	},

	fieldDefaults: {
		msgTarget: 'side'
	},

	bodyPadding: 10,

	initComponent: function() {

		var me = this;

		me.items = [ {
			itemId: 'email',
			name: 'email',
			fieldLabel: i18n.user_email,
			vtype: 'email',
			allowBlank: false,
			maxLength: 255,
			enforceMaxLength: true
		}, {
			name: 'firstName',
			fieldLabel: i18n.user_firstname,
			allowBlank: true,
			maxLength: 255,
			enforceMaxLength: true
		}, {
			name: 'name',
			fieldLabel: i18n.user_name,
			allowBlank: true,
			maxLength: 255,
			enforceMaxLength: true
		}, {
			name: 'passwordHash',
			fieldLabel: i18n.user_password,
			inputType: 'password',
			itemId: 'pass'
		}, {
			name: 'password-confirm',
			fieldLabel: i18n.user_confirmpassword,
			vtype: 'password',
			inputType: 'password',
			initialPassField: 'pass'
		}, {
			xtype: 'combobox',
			fieldLabel: i18n.user_language,
			name: 'locale',
			store: Ext.create('Ext.data.ArrayStore', {
				fields: [ 'code', 'language' ],
				data: [ [ 'de', i18n.user_language_german ], [ 'en', i18n.user_language_english ] ]
			}),
			valueField: 'code',
			displayField: 'language',
			queryMode: 'local',
			emptyText: i18n.user_selectlanguage,
			allowBlank: false,
			forceSelection: true
		}, {
			fieldLabel: i18n.user_active,
			name: 'enabled',
			xtype: 'checkboxfield',
			inputValue: 'true',
			uncheckedValue: 'false'
		}, {
			xtype: 'multiselect',
			name: 'roleIds',
			fieldLabel: i18n.user_roles,
			store: Ext.create('E4desk.store.Roles'),
			displayField: 'name',
			valueField: 'id',
			allowBlank: true
		}];

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