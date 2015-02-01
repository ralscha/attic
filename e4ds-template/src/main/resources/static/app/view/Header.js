Ext.define('E4ds.view.Header', {
	extend: 'Ext.container.Container',
	height: 35,
	layout: {
		type: 'hbox',
		align: 'stretch'
	},

	initComponent: function() {
		var me = this;
		me.items = [ {
			html: i18n.app_title,
			cls: 'appHeader'
		}, {
			xtype: 'tbspacer',
			flex: 1
		}, {
			xtype: 'button',
			itemId: 'loggedOnLabel',
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
					text: i18n.settings,
					glyph: 0xe809,
					itemId: 'settingsButton'
				}, {
					text: i18n.logout,
					glyph: 0xe802,
					href: 'logout',
					hrefTarget: '_self'
				} ]
			}
		} ];

		me.callParent(arguments);

	}

});