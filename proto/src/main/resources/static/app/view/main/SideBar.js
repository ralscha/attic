Ext.define('Proto.view.main.SideBar', {
	extend: 'Ext.tree.Panel',
	title: i18n.navigation,
	collapsible: true,
	width: 180,
	minWidth: 180,
	maxWidth: 250,
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