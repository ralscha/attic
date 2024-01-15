Ext.define('BitP.view.bedarf.Edit', {
	extend: 'Ext.window.Window',
	stateId: 'BitP.view.bedarf.Edit',
	title: 'Bedarf',
	layout: 'fit',
	autoShow: true,
	resizable: true,
	constrain: true,
	width: 500,
	modal: true,

	glyph: 0xe803,

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
				name: 'titel',
				itemId: 'titel',
				fieldLabel: 'Titel',
				allowBlank: false
			}, {
				xtype: 'displayfield',
				name: 'status',
				fieldLabel: 'Status'
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