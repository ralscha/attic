Ext.define('Ab.view.user.Edit', {
	extend: 'Ext.window.Window',
	inject: 'rolesStore',
	title: i18n.user,
	layout: 'fit',
	autoShow: true,
	resizable: true,
	constrain: true,
	width: 500,
	modal: true,
	glyph: 0xe803,
	defaultFocus: 'field[name=userName]',

	requires: [ 'Ext.ux.form.MultiSelect' ],

	initComponent: function() {
		var me = this;

		me.items = [ {
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
				store: me.rolesStore,
				displayField: 'name',
				valueField: 'name',
				allowBlank: true
			} ],

			buttons: [ {
				xtype: 'button',
				itemId: 'editFormSaveButton',
				text: i18n.save,
				action: 'save',
				glyph: 0xe80d,
				formBind: true
			}, {
				text: i18n.cancel,
				scope: me,
				handler: me.close,
				glyph: 0xe80e
			} ]
		} ];

		me.callParent(arguments);
	}
});