Ext.define('Addressbook.view.ContactInfoEdit', {
	extend: 'Ext.window.Window',
	requires: [ 'Addressbook.store.InfoTypes', 'Addressbook.ux.form.field.ClearCombo', 'Addressbook.store.Countries' ],

	title: 'Edit Contact Info',
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
				name: 'infoType',
				itemId: 'infoTypeCB',
				fieldLabel: 'Type',
				displayField: 'type',
				valueField: 'type',
				store: Ext.create('Addressbook.store.InfoTypes'),
				multiSelect: false,
				editable: false,
				forceSelection: true,
				allowBlank: false
			}, {
				name: 'phone',
				fieldLabel: 'Phone'
			}, {
				name: 'email',
				fieldLabel: 'Email'
			}, {
				name: 'address',
				fieldLabel: 'Address'
			}, {
				name: 'city',
				fieldLabel: 'City'
			}, {
				name: 'state',
				fieldLabel: 'State'
			}, {
				name: 'zip',
				fieldLabel: 'Zip'
			}, {
				xtype: 'clearcombo',
				name: 'country',
				fieldLabel: 'Country',
				displayField: 'name',
				valueField: 'iso',
				store: {
					type: 'countries'
				},
				multiSelect: false,
				editable: false,
				forceSelection: true,
				allowBlank: true
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