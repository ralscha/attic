Ext.define('Addressbook.view.ContactEdit', {
	extend: 'Ext.window.Window',
	inject: [ 'contactGroupsStore' ],
	requires: [ 'Ext.form.field.Date', 'Addressbook.ux.form.field.ClearCombo' ],

	title: 'Edit Contact',
	layout: 'fit',
	autoShow: true,
	resizable: true,
	constrain: true,
	width: 500,
	modal: true,

	icon: 'resources/images/edit.png',

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
				xtype: 'clearcombo',
				name: 'contactGroupIds',
				fieldLabel: 'Groups',
				displayField: 'name',
				valueField: 'id',
				store: me.contactGroupsStore,
				multiSelect: true,
				editable: false,
				forceSelection: true
			}, {
				name: 'lastName',
				itemId: 'lastNameTextField',
				fieldLabel: 'Last Name'
			}, {
				name: 'firstName',
				fieldLabel: 'First Name'
			}, {
				xtype: 'datefield',
				fieldLabel: 'Date of Birth',
				name: 'dateOfBirth',
				maxValue: new Date(),
				format: 'd.m.Y'
			}, {
				xtype: 'textareafield',
				grow: true,
				name: 'notes',
				anchor: '100% -60',
				fieldLabel: 'Notes'
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