Ext.define('Changelog.view.loggingevent.List', {
	extend: 'Ext.grid.Panel',
	controller: 'Changelog.controller.LoggingEventController',
	
	title: i18n.logevents,
	closable: true,

	requires: [ 'Ext.grid.plugin.RowExpander', 'Changelog.ux.form.field.ClearCombo' ],

	plugins : [ {
		ptype: 'rowexpander',
		expandOnEnter: false,
		expandOnDblClick: false,
		selectRowOnExpand: true,			
		rowBodyTpl: [ '<tpl if="stacktrace">', '<p>{stacktrace}</p>', '</tpl>', '<tpl if="!stacktrace">',
				'<p>{message}</p>', '</tpl>' ]
	} ],
	
	initComponent: function() {
		var me = this;

		me.store = Ext.create('Changelog.store.LoggingEvents');
		
		me.columns = [ {
			text: i18n.logevents_timestamp,
			dataIndex: 'dateTime',
			width: 160,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
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
			width: 110
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.textexport,
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
				fieldLabel: i18n.filter,
				labelWidth: 40,
				itemId: 'logLevelFilter',
				name: 'logLevelFilter',
				store: Ext.create('Changelog.store.LogLevels'),
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