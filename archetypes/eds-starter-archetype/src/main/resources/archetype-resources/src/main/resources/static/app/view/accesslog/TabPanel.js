Ext.define('${jsAppNamespace}.view.accesslog.TabPanel', {
	extend: 'Ext.tab.Panel',
	requires: [ '${jsAppNamespace}.view.accesslog.Grid', '${jsAppNamespace}.view.accesslog.UaGraph',
			'${jsAppNamespace}.view.accesslog.OsGraph' ],
	controller: '${jsAppNamespace}.controller.AccessLog',

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
