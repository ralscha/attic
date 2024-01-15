Ext.define('Proto.view.person.Window', {
	extend: 'Ext.window.Window',
	requires: [ 'Proto.view.person.WindowController', 'Proto.Util' ],
	stateId: 'Proto.view.person.Window',
	stateful: true,	
	
	layout: 'fit',
	width: 500,
	resizable: true,
	constrain: false,
	modal: true,
	autoShow: false,
	ghost: false,
	glyph: 0xe803,
	defaultFocus: 'lastName',

	controller: {
		xclass: 'Proto.view.person.WindowController'
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
			itemId: 'lastName',
			name: 'lastName',
			fieldLabel: 'Last Name',
			allowBlank: false
		}, {
			name: 'firstName',
			fieldLabel: 'First Name',
			allowBlank: false
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