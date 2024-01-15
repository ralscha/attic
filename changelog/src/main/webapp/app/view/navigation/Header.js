Ext.define('Changelog.view.navigation.Header', {
	extend: 'Ext.container.Container',
	height: 35,
	layout: {
		type: 'hbox',
		align: 'stretch'
	},

	initComponent: function() {
		var me = this;
		me.items = [ {
			html: 'CT ChangeLog',
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
					text: i18n.options,
					icon: app_context_path + '/resources/images/gear.png',
					itemId: 'optionButton'
				}, {
					text: i18n.logout,
					icon: app_context_path + '/resources/images/logout.png',
					href: 'logout',
					hrefTarget: '_self'
				} ]
			}
		}];

		me.callParent(arguments);

	}

});