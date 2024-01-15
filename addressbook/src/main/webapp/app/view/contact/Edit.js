Ext.define('Ab.view.contact.Edit', {
	extend: 'Ext.window.Window',
	inject: [ 'contactGroupsStore' ],
	requires: [ 'Ext.form.field.Date', 'Ab.ux.form.field.ClearCombo' ],
	title: 'Contact',
	layout: 'fit',
	autoShow: true,
	resizable: true,
	constrain: true,
	width: 500,
	modal: true,
	glyph: 0xe803,
	defaultFocus: 'field[name=contactGroupIds]',

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