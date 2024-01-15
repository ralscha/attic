Ext.define('Ab.view.SideBar', {
	extend: 'Ext.tree.Panel',
	requires: ['Ab.view.contact.List'],
	inject: 'navigationStore',
	title: i18n.navigation,
	itemId: 'menuTree',
	collapsible: true,
	layout: 'fit',
	minWidth: 100,
	maxWidth: 200,
	border: true,
	rootVisible: false,
	animate: false,

	initComponent: function() {
		this.store = this.navigationStore;
		this.callParent();
	}

});