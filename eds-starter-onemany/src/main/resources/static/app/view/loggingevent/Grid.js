Ext.define('Starter.view.loggingevent.Grid', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Starter.view.loggingevent.Controller', 'Starter.view.loggingevent.ViewModel' ],
	title: i18n.navigation_security_logevents,
	icon: 'resources/images/data_scroll.png',

	controller: {
		xclass: 'Starter.view.loggingevent.Controller'
	},

	viewModel: {
		xclass: 'Starter.view.loggingevent.ViewModel'
	},

	viewConfig: {
		loadMask: false
	},

	bind: '{loggingEvents}',

	columns: [ {
		text: i18n.logevents_timestamp,
		dataIndex: 'dateTime',
		width: 170,
		xtype: 'datecolumn',
		format: 'Y-m-d H:i:s'
	}, {
		text: i18n.logevents_level,
		dataIndex: 'level',
		width: 90
	}, {
		text: i18n.logevents_message,
		dataIndex: 'message',
		flex: 1
	}, {
		text: i18n.logevents_class,
		dataIndex: 'callerClass',
		sortable: false,
		width: 500
	}, {
		text: i18n.logevents_line,
		dataIndex: 'callerLine',
		align: 'right',
		sortable: false,
		width: 110
	} ],

	plugins: [ {
		ptype: 'rowexpander',
		expandOnEnter: false,
		expandOnDblClick: false,
		selectRowOnExpand: true,
		rowBodyTpl: [ '<tpl if="stacktrace">', '<p class="monospace">{stacktrace}</p>', '</tpl>', '<tpl if="!stacktrace">', '<p class="monospace">{message}</p>', '</tpl>' ]
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.logevents_export,
			glyph: 0xe813,
			href: 'loggingEventExport.txt',
			hrefTarget: '_self',
			bind: {
				params: {
					level: '{levelFilter}'
				}
			}
		}, '-', {
			text: i18n.logevents_deleteall,
			glyph: 0xe806,
			handler: 'deleteAll'
		},
		/* <debug> */
		'-', {
			text: i18n.logevents_addtest,
			glyph: 0xe807,
			handler: 'addTestData'
		},
		/* </debug> */
		'->', {
			xtype: 'combobox',
			emptyText: i18n.filter,
			labelWidth: 40,
			name: 'logLevelFilter',
			bind: {
				store: '{logLevels}'
			},
			valueField: 'level',
			displayField: 'level',
			queryMode: 'local',
			editable: false,
			listeners: {
				change: 'filterLogLevel'
			},
			triggers: {
				clear: {
					type: 'clear',
					hideWhenEmpty: false
				}
			}
		} ]
	}, {
		xtype: 'pagingtoolbar',
		dock: 'bottom',
		bind: {
			store: '{loggingEvents}'
		}
	} ]

});