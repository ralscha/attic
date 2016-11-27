Ext.define('Starter.view.main.Header', {
	extend: 'Ext.container.Container',

	height: 35,
	layout: {
		type: 'hbox'
	},

	items: [ {
		xtype: 'image',
		src: 'resources/images/favicon-32x32.png',
		width: 32,
		height: 32
	}, {
		xtype: 'container',
		html: i18n.app_title,
		cls: 'appHeader'
	}, {
		xtype: 'tbspacer',
		flex: 1
	}, {
		xtype: 'button',
		margin: '2 0 5 0',
		ui: 'default-toolbar',
		bind: {
			text: '{loggedOnUser}'
		},
		glyph: 0xe809,
		handler: 'onUserSettingsClick'
	}, {
		xtype: 'button',
		margin: '2 0 5 5',
		ui: 'default-toolbar',
		text: i18n.logout,
		glyph: 0xe802,
		href: 'logout',
		hrefTarget: '_self'
	} ]

});