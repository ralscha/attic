Ext.define('Packt.view.Login', {
	extend: 'Ext.window.Window',
	
	requires: [ 'Packt.view.Translation' ],

	alias: 'widget.login',
	autoShow: true,
	height: 170,
	width: 450,
	layout: {
		type: 'fit'
	},
	iconCls: 'key',
	title: i18n.login,
	closeAction: 'hide',
	closable: false,

	items: [ {
		xtype: 'form',
		frame: false,
		bodyPadding: 15,
		defaults: {
			xtype: 'textfield',
			anchor: '100%',
			labelWidth: 80,
			allowBlank: false,
			vtype: 'alphanum',
			minLength: 3,
			msgTarget: 'side'
		},
		items: [ {
			name: 'user',
			fieldLabel: i18n.user,
			maxLength: 50
		}, {
			inputType: 'password',
			name: 'password',
			fieldLabel: i18n.password,
			maxLength: 25,
			enableKeyEvents: true,
			id: 'password'
		// vtype: 'customPass'
		} ],
		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'bottom',
			items: [ {
				xtype: 'translation'
			}, {
				xtype: 'tbfill'
			}, {
				xtype: 'button',
				itemId: 'adminLogin',
				iconCls: 'key-go',
				text: 'Login as admin'
			}, {
				xtype: 'button',
				itemId: 'cancel',
				iconCls: 'cancel',
				text: i18n.cancel
			}, {
				xtype: 'button',
				itemId: 'submit',
				formBind: true,
				iconCls: 'key-go',
				text: i18n.submit
			} ]
		} ]
	} ]

});