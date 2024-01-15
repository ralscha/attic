Ext.define('Addressbook.view.Header', {
	extend: 'Ext.container.Container',
	requires: [ 'Ext.layout.container.HBox', 'Ext.toolbar.Spacer' ],

	height: 35,
	layout: {
		type: 'hbox',
		align: 'stretch'
	},

	initComponent: function() {
		var me = this;
		me.items = [ {
			html: 'Addressbook',
			cls: 'appHeader'
		}, {
			xtype: 'tbspacer',
			flex: 1
		}, {
			xtype: 'button',
			icon: 'resources/images/user.png',
			itemId: 'loggedOnLabel',
			hidden: true,
			text: '',
			ui: 'default-toolbar',
			margins: {
				top: 2,
				right: 0,
				bottom: 10,
				left: 0
			},

			menu: {
				items: [ {
					text: 'Logout',
					icon: 'resources/images/logout.png',
					handler: function() {
						Ext.Ajax.request({
							url: '../logout',
							method: 'POST',
							success: function(response) {
								window.location = 'index.html';
							}
						});
					}
				} ]
			}
		} ];

		me.callParent(arguments);

	}

});