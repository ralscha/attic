Ext.define('Changelog.view.accesslog.List', {
	extend: 'Ext.grid.Panel',
	controller: 'Changelog.controller.AccessLogController',		
	title: i18n.accesslog,
	closable: true,

	requires: [ 'Changelog.ux.form.field.FilterField' ],
	
	initComponent: function() {
		var me = this;

		me.store = Ext.create('Changelog.store.AccessLogs');
		
		me.columns = [ {
			text: i18n.user_username,
			dataIndex: 'userName',
			flex: 1
		}, {
			text: i18n.accesslog_browser,
			dataIndex: 'browser',
			width: 180,
			sortable: false
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
				icon: app_context_path + '/resources/images/eraser.png'
			}, /* <debug> */'-', {
				text: i18n.accesslog_testinsert,
				itemId: 'testButton',
				icon: app_context_path + '/resources/images/add.png'
			}, /* </debug> */'->', {
				fieldLabel: i18n.user_username,
				itemId: 'filterField',
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store,
			displayInfo: true,
			displayMsg: i18n.accesslog_display,
			emptyMsg: i18n.accesslog_nodata
		} ];

		me.callParent(arguments);

	}

});