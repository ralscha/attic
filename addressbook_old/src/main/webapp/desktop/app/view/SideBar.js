Ext.define('Addressbook.view.SideBar', {
	extend: 'Ext.tree.Panel',
	inject: 'navigationStore',
	requires: [ 'Addressbook.store.Navigation' ],

	itemId: 'menuTree',

	title: 'Menu',
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