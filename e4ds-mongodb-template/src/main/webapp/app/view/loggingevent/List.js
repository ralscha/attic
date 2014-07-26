Ext.define('E4ds.view.loggingevent.List', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.loggingeventlist',
	store: 'LoggingEvents',
	
	title: i18n.logevents,
	closable: true,

	requires: [ 'Ext.ux.RowExpander', 'Ext.ux.form.field.ClearButton', 'Ext.ux.form.field.ClearButton' ],

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
		
		me.columns = [ {
			text: i18n.logevents_timestamp,
			dataIndex: 'timeStamp',
			width: 200,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
		}, {
			text: i18n.user_username,
			dataIndex: 'userName',
			width: 100
		}, {
			text: i18n.logevents_level,
			dataIndex: 'level',
			width: 70
		}, {
			text: i18n.logevents_message,
			dataIndex: 'message',
			flex: 1
		}, {
			text: i18n.logevents_caller,
			dataIndex: 'caller',
			flex: 1
		} ];

		
		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.excelexport,
				action: 'export',
				iconCls: 'icon-excel',
				href: 'loggingEventExport.xls',				
				hrefTarget: '_self',
			}, '-', {
				text: i18n.logevents_delete,
				action: 'deleteall',
				iconCls: 'icon-delete'
			}, '-', {
				text: i18n.logevents_addtest,
				action: 'test',
				iconCls: 'icon-add'
			}, '->',{
				xtype: 'combobox',
				fieldLabel: i18n.filter,
				labelWidth: 40,
				name: 'logLevelFilter',
				store: 'LogLevels',
				valueField: 'level',
				displayField: 'level',
				queryMode: 'local',
				forceSelection: true,
				plugins: new Ext.ux.form.field.ClearButton({hideClearButtonWhenEmpty: false, hideClearButtonWhenMouseOut: false})
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: 'LoggingEvents',
			displayInfo: true,
			displayMsg: i18n.logevents_display,
			emptyMsg: i18n.logevents_no
		} ];

		me.callParent(arguments);

	}

});