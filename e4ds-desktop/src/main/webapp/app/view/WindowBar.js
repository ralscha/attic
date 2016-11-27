Ext.define('E4desk.view.WindowBar', {
	extend: 'Ext.toolbar.Toolbar',
	itemId: 'windowBar',

	cls: 'windowbar',
	layout: {
		overflowHandler: 'Scroller'
	},

	contextMenu: Ext.create('Ext.menu.Menu', {
		defaultAlign: 'br-tr',
		items: [ {
			text: i18n.desktop_restore,
			action: 'restore'
		}, {
			text: i18n.desktop_minimize,
			action: 'minimize'
		}, {
			text: i18n.desktop_maximize,
			action: 'maximize'
		}, '-', {
			text: i18n.desktop_close,
			action: 'close'
		} ]
	}),

	initComponent: function() {
		var me = this;

		me.items = [ '->', {
			id: 'server_connect_status',
			xtype: 'image',
			src: app_context_path + '/resources/images/connect-off.gif',
			width: 20,
			height: 16,
			margin: '5 5 7 5'
		} ];

		me.callParent(arguments);
	}

});
