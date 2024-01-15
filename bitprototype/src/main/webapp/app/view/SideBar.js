Ext.define('BitP.view.SideBar', {
	extend: 'Ext.panel.Panel',
	requires: [ 'BitP.view.user.List', 'BitP.view.accesslog.List', 'BitP.view.logevent.List', 'BitP.view.config.Edit' ],
	title: i18n.navigation,
	collapsible: true,
	layout: 'fit',
	minWidth: 100,
	maxWidth: 200,

	initComponent: function() {
		var me = this;
		me.items = [ {
			xtype: 'treepanel',
			itemId: 'menuTree',
			border: 0,
			store: Ext.create('BitP.store.Navigation'),
			rootVisible: false,
			animate: false
		} ];

		me.callParent(arguments);
	}
});