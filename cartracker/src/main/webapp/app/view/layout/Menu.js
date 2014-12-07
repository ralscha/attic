/**
 * Main top-level navigation for application
 */
Ext.define('CarTracker.view.layout.Menu', {
	extend: 'Ext.menu.Menu',
	alias: 'widget.layout.menu',
	floating: false,
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			items: [ {
				text: 'Executive Dashboard',
				itemId: 'dashboard',
				iconCls: 'icon_dashboard',
				hidden: !CarTracker.loggedInUser.inRole('ADMIN')
			}, {
				xtype: 'menuseparator',
				hidden: !CarTracker.loggedInUser.inRole('ADMIN')
			}, {
				text: 'Options',
				iconCls: 'icon_gear',
				hidden: !CarTracker.loggedInUser.inRole('ADMIN'),
				menu: [ {
					text: 'Car Makes',
					itemId: 'option/make',
					iconCls: 'icon_make'
				}, {
					text: 'Car Models',
					itemId: 'option/model',
					iconCls: 'icon_model'
				}, {
					text: 'Car Categories',
					itemId: 'option/category',
					iconCls: 'icon_category'
				}, {
					text: 'Car Colors',
					itemId: 'option/color',
					iconCls: 'icon_color'
				}, {
					text: 'Car Features',
					itemId: 'option/feature',
					iconCls: 'icon_feature'
				}, {
					text: 'Staff Positions',
					itemId: 'option/position',
					iconCls: 'icon_position'
				}, {
					text: 'Drive Trains',
					itemId: 'option/drivetrain',
					iconCls: 'icon_drivetrain'
				} ]
			}, {
				xtype: 'menuseparator',
				hidden: !CarTracker.loggedInUser.inRole('ADMIN')
			}, {
				text: 'Sales Staff',
				itemId: 'staff',
				iconCls: 'icon_user',
				hidden: !CarTracker.loggedInUser.inRole('ADMIN')
			}, {
				xtype: 'menuseparator',
				hidden: !CarTracker.loggedInUser.inRole('ADMIN')
			}, {
				text: 'Inventory',
				itemId: 'inventory',
				iconCls: 'icon_tag'
			}, {
				xtype: 'menuseparator',
				hidden: !CarTracker.loggedInUser.inRole('ADMIN')
			}, {
				text: 'Reports',
				iconCls: 'icon_report',
				hidden: !CarTracker.loggedInUser.inRole('ADMIN'),
				menu: [ {
					text: 'Sales by Make',
					itemId: 'report/make',
					iconCls: 'icon_piechart'
				}, {
					text: 'Sales by Month',
					itemId: 'report/month',
					iconCls: 'icon_barchart'
				} ]
			}, {
				xtype: 'menuseparator'
			}, {
				text: 'Logout',
				itemId: 'logout',
				iconCls: 'icon_login'
			} ]
		});
		me.callParent(arguments);
	}
});