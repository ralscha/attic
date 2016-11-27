Ext.define('Starter.view.accesslog.Grid', {
	extend: 'Ext.grid.Panel',

	title: i18n.accesslog_log,
	bind: '{accessLogs}',

	viewConfig: {
		loadMask: false
	},
	
	columns: [ {
		text: i18n.accesslog_user,
		dataIndex: 'loginName',
		width: 200
	}, {
		text: i18n.accesslog_ip,
		dataIndex: 'ipAddress',
		flex: 1
	}, {
		text: i18n.accesslog_location,
		dataIndex: 'location',
		flex: 1
	}, {
		text: i18n.accesslog_userAgent,
		dataIndex: 'userAgentName',
		flex: 1
	}, {
		text: i18n.accesslog_userAgentVersion,
		dataIndex: 'userAgentVersion',
		width: 100
	}, {
		text: i18n.accesslog_operatingSystem,
		dataIndex: 'operatingSystem',
		width: 200
	}, {
		text: i18n.accesslog_login,
		dataIndex: 'loginTimestamp',
		width: 150,
		xtype: 'datecolumn',
		format: 'Y-m-d H:i:s'
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.accesslog_deleteall,
			glyph: 0xe806,
			handler: 'deleteAll'
		}, /* <debug> */'-', {
			text: i18n.accesslog_testinsert,
			glyph: 0xe807,
			handler: 'addTestData'
		}, /* </debug> */'->', {
			emptyText: i18n.accesslog_user,
			xtype: 'textfield',
			triggers: {
				search: {
					cls: Ext.baseCSSPrefix + 'form-search-trigger',
					handler: 'onUserFilter'
				},
				clear: {
					type: 'clear',
					hideWhenEmpty: false,
					handler: 'onUserFilter'
				}
			},
			listeners: {
				specialKey: 'onUserFilterSpecialKey'
			}
		} ]
	}, {
		xtype: 'pagingtoolbar',
		dock: 'bottom',
		bind: {
			store: '{accessLogs}'
		}
	} ]

});