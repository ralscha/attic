Ext.define('E4ds.view.accesslog.TabPanel', {
	extend: 'Ext.tab.Panel',
	requires: [ 'E4ds.view.accesslog.Grid', 'E4ds.view.accesslog.UaGraph',
			'E4ds.view.accesslog.OsGraph' ],
	controller: 'E4ds.controller.AccessLog',

	title: i18n.accesslog,
	closable: true,
	border: true,
	plain: true,
	padding: '2 0 0 0',

	initComponent: function() {
		this.items = [ {
			xtype: 'accesslog'
		}, {
			xtype: 'uagraph'
		}, {
			xtype: 'osgraph'
		} ];
		this.callParent(arguments);
	}

});
