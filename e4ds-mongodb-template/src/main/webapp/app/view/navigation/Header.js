Ext.define('E4ds.view.navigation.Header', {
	alias: 'widget.navigationheader',
	extend: 'Ext.container.Container',
	height: 35,
	layout: {
		type: 'hbox',
		align: 'stretch'
	},

	initComponent: function() {
		var me = this;
		me.items = [ {
			html: 'e4ds-template-mongodb',
		cls: 'appHeader'
	}, {
		xtype: 'tbspacer',
		flex: 1
	}, {
		xtype: 'label',
			text: '',
			cls: 'userName',
		width: 200,
		margins: {
			top: 6,
			right: 0,
			bottom: 0,
			left: 0
		}
	}, {
			xtype: 'button',
			text: i18n.options,
			iconCls: 'icon-gear',
			action: 'options',
			margins: {
				top: 2,
				right: 0,
				bottom: 10,
				left: 0
			}			
		}, {
		xtype: 'tbspacer',
		width: 20,
	}, {
		xtype: 'button',
		text: i18n.logout,
			iconCls: 'icon-logout',
		href: 'j_spring_security_logout',
			hrefTarget: '_self',
		margins: {
			top: 2,
			right: 0,
			bottom: 10,
			left: 0
		}
		} ];

		me.callParent(arguments);

	}

});