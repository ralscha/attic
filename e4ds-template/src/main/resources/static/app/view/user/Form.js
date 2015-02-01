Ext.define('E4ds.view.user.Form', {
	extend: 'Ext.form.Panel',
	inject: 'rolesStore',
	layout: 'fit',
	width: 400,
	modal: true,
	border: true,
	glyph: 0xe803,

	requires: [ 'Ext.ux.form.MultiSelect' ],

	initComponent: function() {
		this.items = [ {
			xtype: 'form',
			padding: 5,
			bodyPadding: 10,

			defaultType: 'textfield',
			defaults: {
				anchor: '100%'
			},

			fieldDefaults: {
				msgTarget: 'side'
			},

			items: [ {
				name: 'userName',
				fieldLabel: i18n.user_username,
				allowBlank: false
			}, {
				name: 'firstName',
				fieldLabel: i18n.user_firstname,
				allowBlank: false
			}, {
				name: 'name',
				fieldLabel: i18n.user_lastname,
				allowBlank: false
			}, {
				name: 'email',
				fieldLabel: i18n.user_email,
				vtype: 'email',
				allowBlank: false
			}, {
				name: 'passwordNew',
				fieldLabel: i18n.user_password,
				inputType: 'password'
			}, {
				name: 'passwordNewConfirm',
				fieldLabel: i18n.user_confirmpassword,
				inputType: 'password'
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
				fieldLabel: i18n.user_enabled,
				name: 'enabled',
				xtype: 'checkboxfield',
				inputValue: 'true',
				uncheckedValue: 'false'
			}, {
				xtype: 'multiselect',
				name: 'role',
				fieldLabel: i18n.user_roles,
				store: this.rolesStore,
				displayField: 'name',
				valueField: 'name',
				allowBlank: true
			} ],

			buttons: [ {
				xtype: 'button',
				itemId: 'formSaveButton',
				text: i18n.save,
				action: 'save',
				glyph: 0xe80d,
				formBind: true
			}, {
				text: i18n.close,
				itemId: 'formCloseButton',
				glyph: 0xe80e
			} ]
		} ];

		this.callParent(arguments);
	}
});