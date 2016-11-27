Ext.define('E4desk.view.AccessLogWindow', {
	extend: 'Ext.window.Window',
	requires: ['E4desk.view.AccessLogGrid'],
	controller: 'E4desk.controller.AccessLog',
	stateId: 'E4desk.view.AccessLogWindow',

	title: i18n.accesslog,
	width: 840,
	height: 560,
	iconCls: 'accesslog-icon',
	layout: 'fit',

	initComponent: function() {
		this.items = Ext.create('E4desk.view.AccessLogGrid', {
			itemId: 'grid'
		});

		this.callParent(arguments);
	}

});
