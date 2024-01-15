Ext.define('Addressbook.view.Viewport', {
	extend: 'Ext.container.Viewport',
	controller: 'Addressbook.controller.Viewport',
	requires: [ 'Ext.layout.container.Border', 'Addressbook.controller.Viewport', 'Addressbook.view.Header', 'Addressbook.view.Login' ],

	style: {
		backgroundColor: 'white'
	},

	layout: {
		type: 'border',
		padding: 5
	},

	defaults: {
		split: true
	},

	initComponent: function() {
		this.items = [ Ext.create('Addressbook.view.Header', {
			region: 'north',
			split: false
		}), Ext.create('Ext.Container', {
			layout: {
				type: 'vbox',
				align: 'center',
				pack: 'center'
			},
			region: 'center',
			itemId: 'login',
			items: {
				xtype: 'login'
			}
		}) ];

		this.callParent();
	}

});
