Ext.define('${jsAppNamespace}.view.SideBar', {
	extend: 'Ext.tree.Panel',
	requires: [ '${jsAppNamespace}.view.poll.PollChart', '${jsAppNamespace}.view.user.Container',
			'${jsAppNamespace}.view.accesslog.TabPanel', '${jsAppNamespace}.view.loggingevent.List',
			'${jsAppNamespace}.view.config.Edit', '${jsAppNamespace}.store.Navigation' ],
	title: i18n.navigation,
	itemId: 'menuTree',
	collapsible: true,
	layout: 'fit',
	minWidth: 100,
	maxWidth: 200,
	border: true,
	rootVisible: false,
	animate: false,
	store: Ext.create('${jsAppNamespace}.store.Navigation')
});