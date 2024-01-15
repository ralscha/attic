Ext.define('BitP.view.accesslog.List', {
	extend: 'Ext.grid.Panel',
	controller: 'BitP.controller.AccessLog',
	title: i18n.accesslog,
	closable: true,
	border: true,

	requires: [ 'BitP.ux.form.field.FilterField', 'BitP.store.AccessLogs' ],

	initComponent: function() {
		var me = this;

		me.store = Ext.create('BitP.store.AccessLogs');

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
				glyph: 0xe806
			}, /* <debug> */'-', {
				text: i18n.accesslog_testinsert,
				itemId: 'testButton',
				glyph: 0xe807
			}, /* </debug> */, '->', {
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