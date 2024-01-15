Ext.define('Addressbook.view.AppUserEdit', {
	extend: 'Ext.window.Window',

	title: 'Edit User',
	layout: 'fit',
	autoShow: true,
	resizable: true,
	constrain: true,
	width: 500,
	modal: true,

	icon: 'resources/images/edit.png',

	requires: [ 'Ext.form.field.Checkbox' ],

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
				itemId: 'userNameTextField',
				fieldLabel: 'Username',
				allowBlank: false
			}, {
				name: 'firstName',
				fieldLabel: 'First Name',
				allowBlank: true
			}, {
				name: 'name',
				fieldLabel: 'Last Name',
				allowBlank: true
			}, {
				name: 'email',
				fieldLabel: 'Email',
				vtype: 'email',
				allowBlank: false
			}, {
				name: 'passwordNew',
				fieldLabel: 'Password',
				inputType: 'password'
			}, {
				name: 'passwordNewConfirm',
				fieldLabel: 'Confirm Password',
				inputType: 'password'
			}, {
				fieldLabel: 'Enabled',
				name: 'enabled',
				xtype: 'checkboxfield',
				inputValue: 'true',
				uncheckedValue: 'false'
			} ],

			buttons: [ {
				xtype: 'button',
				itemId: 'editFormSaveButton',
				text: 'Save',
				action: 'save',
				icon: 'resources/images/save.png',
				formBind: true
			}, {
				text: 'Cancel',
				scope: me,
				handler: me.close,
				icon: 'resources/images/cancel.png'
			} ]
		} ];

		me.callParent(arguments);
	}
});