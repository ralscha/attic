Ext.define('E4desk.view.AccessLogGrid', {
	extend: 'Ext.grid.Panel',

	requires: [ 'E4desk.ux.form.field.FilterField', 'E4desk.store.AccessLogs' ],

	initComponent: function() {
		var me = this;

		me.store = Ext.create('E4desk.store.AccessLogs');

		me.columns = [ {
			text: i18n.accesslog_user,
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
				fieldLabel: i18n.accesslog_user,
				itemId: 'filterField',
				labelWidth: 50,
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