Ext.define('Changelog.view.log.Changes', {
	extend: 'Ext.grid.Panel',	
	itemId: 'logChanges',
	title: i18n.change_changes,
	
	requires: [ 'Ext.grid.plugin.RowExpander' ],

	plugins: [ {
		ptype: 'rowexpander',
		expandOnEnter: false,
		expandOnDblClick: false,
		selectRowOnExpand: false,		
		rowBodyTpl: [ '<p>{description}</p>' ]
	} ],
	
	initComponent: function() {
		var me = this;
		me.store = Ext.create('Changelog.store.LogChanges');
		me.columns = [ {
			dataIndex: 'typ',
			text: i18n.change_typ,
			width: 100,
			renderer: function(value) {
				if (value === 'FIX') {
					return i18n.change_typ_fix;
				} 
				if (value === 'ENHANCEMENT') {
					return i18n.change_typ_enhancement;
				} 
				if (value === 'NEW') {
					return i18n.change_typ_new;
				}
				return '';
			}			
		}, {
			dataIndex: 'module',
			width: 120,
			text: i18n.change_module
		}, {
			dataIndex: 'bugNumber',
			width: 80,
			text: i18n.change_bugno
		}, {
			dataIndex: 'subject',
			flex: 1,
			text: i18n.change_description
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.excelexport,
				itemId: 'exportButton',
				disabled: true,
				icon: app_context_path + '/resources/images/excel.gif',
				href: 'customerBuildExcelExport.xls',
				hrefTarget: '_self'
			} ]
		} ];
		
		
		me.callParent(arguments);
	}
});