Ext.define('Proto.view.project.Window', {
	extend: 'Ext.window.Window',
	requires: [ 'Proto.view.project.WindowController', 'Proto.Util' ],
	stateId: 'Proto.view.project.Window',
	stateful: true,	
	
	layout: 'fit',
	width: 500,
	resizable: true,
	constrain: false,
	modal: true,
	autoShow: false,
	ghost: false,
	glyph: 0xe803,
	defaultFocus: 'name',

	controller: {
		xclass: 'Proto.view.project.WindowController'
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
			itemId: 'name',
			name: 'name',
			fieldLabel: 'Name',
			allowBlank: false
		}, {
			xtype: 'datefield',
			name: 'ms1',
			fieldLabel: 'MS1',
			format: 'd.m.Y',
			anchor: '50%'
		}, {
			xtype: 'datefield',
			name: 'ms2',
			fieldLabel: 'MS2',
			format: 'd.m.Y',
			anchor: '50%'
		}, {
			xtype: 'datefield',
			name: 'ms3',
			fieldLabel: 'MS3',
			format: 'd.m.Y',
			anchor: '50%'
		}, {
			xtype: 'datefield',
			name: 'ms4',
			fieldLabel: 'MS4',
			format: 'd.m.Y',
			anchor: '50%'
		}, {
			xtype: 'datefield',
			name: 'ms5',
			fieldLabel: 'MS5',
			format: 'd.m.Y',
			anchor: '50%'
		}, {
			xtype: 'datefield',
			name: 'ms6',
			fieldLabel: 'MS6',
			format: 'd.m.Y',
			anchor: '50%'
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