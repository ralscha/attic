Ext.define('${jsAppNamespace}.view.accesslog.Grid', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.accesslog',

	title: 'Log',

	requires: [ '${jsAppNamespace}.ux.form.field.FilterField', '${jsAppNamespace}.store.AccessLogs' ],

	initComponent: function() {
		var me = this;

		me.store = Ext.create('${jsAppNamespace}.store.AccessLogs');

		me.columns = [ {
			text: i18n.user_username,
			dataIndex: 'userName',
			width: 200
		}, {
			text: i18n.accesslog_userAgent,
			dataIndex: 'userAgentName',
			flex: 1
		}, {
			text: i18n.accesslog_userAgentVersion,
			dataIndex: 'userAgentVersion',
			width: 100
		}, {
			text: i18n.accesslog_operatingSystem,
			dataIndex: 'operatingSystem',
			width: 200
		}, {
			text: i18n.accesslog_login,
			dataIndex: 'logIn',
			width: 150,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
		}, {
			text: i18n.accesslog_logout,
			dataIndex: 'logOut',
			width: 150,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
		}, {
			text: i18n.accesslog_duration,
			dataIndex: 'duration',
			width: 200,
			sortable: false
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.accesslog_deleteall,
				itemId: 'deleteAllButton',
				glyph: 0xe806
			}, /* <debug> */'-', {
				text: i18n.accesslog_testinsert,
				itemId: 'testButton',
				glyph: 0xe807
			}, /* </debug> */'->', {
				fieldLabel: i18n.user_username,
				itemId: 'filterField',
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store
		} ];

		me.callParent(arguments);

	}

});