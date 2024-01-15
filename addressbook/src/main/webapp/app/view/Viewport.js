Ext.define('Ab.view.Viewport', {
	extend: 'Ext.Viewport',
	controller: 'Ab.controller.Viewport',
	requires: [ 'Ext.ux.TabReorderer', 'Ext.ux.TabCloseMenu', 'Ab.view.Header', 'Ab.view.SideBar', 'Ab.store.Navigation', 'Ab.view.Login', 'Ab.view.option.List' ],

	style: {
		backgroundColor: '#F8F8F8'
	},

	layout: {
		type: 'border',
		padding: 5
	},

	defaults: {
		split: true
	},

	initComponent: function() {
		this.items = [ Ext.create('Ab.view.Header', {
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
	
//	initComponent: function() {
//		var me = this;
//
//		var tabCloseMenu = Ext.create('Ext.ux.TabCloseMenu');
//		tabCloseMenu.closeTabText = i18n.tabclosemenu_close;
//		tabCloseMenu.closeOthersTabsText = i18n.tabclosemenu_closeother;
//		tabCloseMenu.closeAllTabsText = i18n.tabclosemenu_closeall;
//
//		me.items = [ Ext.create('Ab.view.Header', {
//			region: 'north',
//			split: false
//		}), {
//			region: 'center',
//			xtype: 'tabpanel',
//			itemId: 'tabPanel',
//			plugins: [ Ext.create('Ext.ux.TabReorderer'), tabCloseMenu ],
//			plain: true
//		}, Ext.create('Ab.view.SideBar', {
//			region: 'west',
//			width: 180
//		}) ];
//
//		me.callParent(arguments);
//	}

});