Ext.define('E4ds.view.accesslog.List', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.accessloglist',
	store: 'AccessLogs',
	stateId: 'accesslogList',
	
	title: i18n.accesslogs,
	closable: true,

	initComponent: function() {
		var me = this;

		me.columns = [ {
			text: i18n.user_username,
			dataIndex: 'userName',
			flex: 1
		}, {
			text: i18n.accesslogs_login,
			dataIndex: 'logIn',
			width: 150,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
		}, {
			text: i18n.accesslogs_logout,
			dataIndex: 'logOut',
			width: 150,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
		}, {
			text: i18n.accesslogs_durationinseconds,
			dataIndex: 'durationInSeconds',
			width: 150,
			sortable: false,
			align: 'right'
		}, {
			text: i18n.accesslogs_sessionid,
			dataIndex: 'sessionId',
			width: 200
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.accesslogs_delete,
				action: 'deleteall',
				iconCls: 'icon-delete'
			}, '-', {
				text: i18n.accesslogs_addtest,
				action: 'test',
				iconCls: 'icon-add'
			}, '->', {
				fieldLabel: i18n.user_username,
				labelWidth: 60,
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: 'AccessLogs',
			displayInfo: true,
			displayMsg: i18n.accesslogs_display,
			emptyMsg: i18n.accesslogs_no
		} ];

		me.callParent(arguments);

	}

});