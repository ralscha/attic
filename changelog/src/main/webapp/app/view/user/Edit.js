Ext.define('Changelog.view.user.Edit', {
	extend: 'Ext.window.Window',
	stateId: 'Changelog.view.user.Edit',
	title: i18n.user_edit,
	layout: 'fit',
	autoShow: true,
	resizable: true,
	constrainHeader: true,
	width: 500,
	height: 420,
	modal: true,

	icon: app_context_path + '/resources/images/edit.png',

	requires: [ 'Ext.ux.form.MultiSelect' ],

	getForm: function() {
		return this.getComponent('userEditForm').getForm();
	},
	
	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'form',
			itemId: 'userEditForm',
			padding: 5,
			bodyPadding: 10,
			bodyBorder: true,

			defaultType: 'textfield',
			defaults: {
				anchor: '100%'
			},

			api: {
				submit: userService.userFormPost
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
				name: 'passwordHash',
				fieldLabel: i18n.user_password,
				inputType: 'password',
				id: 'pass'
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
				fieldLabel: i18n.user_enabled,
				name: 'enabled',
				xtype: 'checkboxfield',
				inputValue: 'true',
				uncheckedValue: 'false'
			}, {
				xtype: 'multiselect',
				name: 'roleIds',
				fieldLabel: i18n.user_roles,
				store: me.rolesStore,
				displayField: 'name',
				valueField: 'id',
				allowBlank: true
			} ],

			buttons: [ {
				xtype: 'button',
				itemId: 'saveButton',
				text: i18n.save,
				action: 'save',
				icon: app_context_path + '/resources/images/save.png',
				disabled: true,
				formBind: true,
				handler: function() { 
					me.controller.updateUser(me);
				}
			}, {
				text: i18n.cancel,
				scope: me,
				handler: me.close,
				icon: app_context_path + '/resources/images/cancel.png'
			} ]
		} ];

		me.callParent(arguments);
	}
});