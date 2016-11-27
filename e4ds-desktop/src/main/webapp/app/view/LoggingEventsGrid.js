Ext.define('E4desk.view.LoggingEventsGrid', {
	extend: 'Ext.grid.Panel',

	requires: [ 'E4desk.ux.form.field.ClearCombo', 'E4desk.store.LogLevels', 'E4desk.store.LoggingEvents' ],

	plugins: [ {
		ptype: 'rowexpander',
		expandOnEnter: false,
		expandOnDblClick: false,
		selectRowOnExpand: true,
		rowBodyTpl: [ '<tpl if="stacktrace">', '<p>{stacktrace}</p>', '</tpl>', '<tpl if="!stacktrace">', '<p>{message}</p>', '</tpl>' ]
	} ],

	initComponent: function() {
		var me = this;

		me.store = Ext.create('E4desk.store.LoggingEvents');

		me.columns = [ {
			text: i18n.logevents_timestamp,
			dataIndex: 'dateTime',
			width: 160,
			xtype: 'datecolumn',
			format: 'd.m.Y H:i:s'
		}, {
			text: i18n.logevents_level,
			dataIndex: 'level',
			width: 70
		}, {
			text: i18n.logevents_message,
			dataIndex: 'message',
			width: 200
		}, {
			text: i18n.logevents_callerclass,
			dataIndex: 'callerClass',
			sortable: false,
			flex: 1
		}, {
			text: i18n.logevents_callerline,
			dataIndex: 'callerLine',
			align: 'right',
			sortable: false,
			width: 70
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.logevents_export,
				itemId: 'exportButton',
				icon: app_context_path + '/resources/images/document_down.png',
				href: 'loggingEventExport.txt',
				hrefTarget: '_self'
			}, '-', {
				text: i18n.logevents_deleteall,
				itemId: 'deleteAllButton',
				icon: app_context_path + '/resources/images/eraser.png'
			},/* <debug> */'-', {
				text: i18n.logevents_addtest,
				itemId: 'testButton',
				icon: app_context_path + '/resources/images/add.png'
			},/* </debug> */'->', {
				xtype: 'clearcombo',
				fieldLabel: i18n.logevents_filter,
				labelWidth: 40,
				itemId: 'logLevelFilter',
				name: 'logLevelFilter',
				store: Ext.create('E4desk.store.LogLevels'),
				valueField: 'level',
				displayField: 'level',
				queryMode: 'local',
				forceSelection: true
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store,
			displayInfo: true,
			displayMsg: i18n.logevents_display,
			emptyMsg: i18n.logevents_nodata
		} ];

		me.callParent(arguments);

	}

});