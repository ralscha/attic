Ext.define('E4ds.view.SideBar', {
	extend: 'Ext.tree.Panel',
	requires: [ 'E4ds.view.poll.PollChart', 'E4ds.view.user.Container',
			'E4ds.view.accesslog.TabPanel', 'E4ds.view.loggingevent.List',
			'E4ds.view.config.Edit', 'E4ds.store.Navigation' ],
	title: i18n.navigation,
	itemId: 'menuTree',
	collapsible: true,
	layout: 'fit',
	minWidth: 100,
	maxWidth: 200,
	border: true,
	rootVisible: false,
	animate: false,
	store: Ext.create('E4ds.store.Navigation')
});