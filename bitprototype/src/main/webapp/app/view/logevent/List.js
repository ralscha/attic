Ext.define('BitP.view.logevent.List', {
	extend: 'Ext.grid.Panel',
	controller: 'BitP.controller.LogEvent',

	title: i18n.logevents,
	closable: true,
	border: true,
	requires: [ 'Ext.grid.plugin.RowExpander', 'BitP.ux.form.field.ClearCombo', 'BitP.store.LogLevels', 'BitP.store.LogEvents' ],

	plugins: [ {
		ptype: 'rowexpander',
		expandOnEnter: false,
		expandOnDblClick: false,
		selectRowOnExpand: true,
		rowBodyTpl: [ '<p><strong>',i18n.logevents_message,': </strong>{message}</p>',
		              '<p><strong>',i18n.logevents_source,': </strong><span class="monospace">{source}</span></p>',		              
		              '<tpl if="exception">', 
		                '<p class="monospace">{exception}</p>', 
		              '</tpl>' ]
	} ],

	initComponent: function() {
		var me = this;

		me.store = Ext.create('BitP.store.LogEvents');

		me.columns = [ {
			text: i18n.logevents_timestamp,
			dataIndex: 'eventDate',
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
			flex: 1
		}, {
			text: i18n.logevents_source,
			dataIndex: 'source',
			flex: 1
		}, {
			text: i18n.user,
			dataIndex: 'userName',
			width: 200
		}];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.textexport,
				itemId: 'exportButton',
				glyph: 0xe813,
				href: 'logEventExport.txt',
				hrefTarget: '_self'
			}, '-', {
				text: i18n.logevents_deleteall,
				itemId: 'deleteAllButton',
				glyph: 0xe806
			},/* <debug> */'-', {
				text: i18n.logevents_addtest,
				itemId: 'testButton',
				glyph: 0xe807
			},/* </debug> */, '->', {
				xtype: 'clearcombo',
				fieldLabel: i18n.filter,
				labelWidth: 40,
				itemId: 'logLevelFilter',
				name: 'logLevelFilter',
				store: Ext.create('BitP.store.LogLevels'),
				valueField: 'level',
				displayField: 'level',
				queryMode: 'local',
				forceSelection: true
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store
		} ];

		me.callParent(arguments);

	}

});