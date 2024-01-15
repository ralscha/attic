Ext.define('Starter.view.main.SideBar', {
	extend: 'Ext.tree.Panel',
	title: i18n.navigation,
	collapsible: true,
	width: 180,
	minWidth: 210,
	maxWidth: 260,
	border: true,
	rootVisible: false,
	animate: false,
	viewConfig: {
		scroll: false
	},
	bind: '{navigationStore}',
	listeners: {
		selectionchange: 'onNavigationTreeSelectionchange'
	}
});