Ext.define('Starter.view.user.Grid', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Starter.view.user.Controller', 'Starter.view.user.ViewModel' ],

	title: i18n.user_users,
	closable: true,

	controller: {
		xclass: 'Starter.view.user.Controller'
	},

	viewModel: {
		xclass: 'Starter.view.user.ViewModel'
	},

	bind: {
		store: '{users}',
		selection: '{selectedUser}'
	},

	listeners: {
		itemdblclick: 'onItemDoubleClick',
		itemcontextmenu: 'onItemContextMenu',
		activate: 'onActivate'
	},

	viewConfig: {
		loadMask: false
	},
	
	columns: [ {
		xtype: 'actioncolumn',
		sortable: false,
		hideable: false,
		width: 30,
		items: [ {
			icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAAK0lEQVR4AWMgBBhXyRFQsPI/xQoyCCgg7EgX2jkSYQWZAOFN2jtSjsKQBAD0NQ+N4ZAsdgAAAABJRU5ErkJggg==',
			handler: 'onActionColumnClick'
		} ]
	}, {
		text: i18n.user_loginname,
		dataIndex: 'loginName',
		flex: 1
	}, {
		text: i18n.user_lastname,
		dataIndex: 'lastName',
		flex: 1
	}, {
		text: i18n.user_firstname,
		dataIndex: 'firstName',
		flex: 1
	}, {
		text: i18n.user_email,
		dataIndex: 'email',
		flex: 1
	}, {
		text: 'Role',
		dataIndex: 'role',
		flex: 1
	}, {
		xtype: 'datecolumn',
		format: 'Y-m-d H:i:s',
		text: i18n.user_lastlogin,
		dataIndex: 'lastLogin',
		width: 150,
		sortable: false
	}, {
		text: i18n.user_enabled,
		dataIndex: 'enabled',
		width: 85,
		defaultRenderer: function(value) {
			if (value === true) {
				return i18n.yes;
			}
			return i18n.no;
		}
	}, {
		text: i18n.user_locked,
		dataIndex: 'lockedOutUntil',
		width: 95,
		defaultRenderer: function(value) {
			if (value) {
				return i18n.yes;
			}
			return i18n.no;
		}
	}],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.create,
			glyph: 0xe807,
			handler: 'newUser'
		}, '-', {
			text: i18n.excelexport,
			glyph: 0xe813,
			href: 'usersExport.xlsx',
			hrefTarget: '_self',
			bind: {
				params: {
					filter: '{filter}'
				}
			}
		}, '->', {
			emptyText: i18n.filter,
			xtype: 'textfield',
			triggers: {
				search: {
					cls: Ext.baseCSSPrefix + 'form-search-trigger',
					handler: 'onFilter'
				},
				clear: {
					type: 'clear',
					hideWhenEmpty: false,
					handler: 'onFilter'
				}
			},
			listeners: {
				specialKey: 'onFilterSpecialKey'
			}
		} ]
	}, {
		xtype: 'pagingtoolbar',
		dock: 'bottom',
		bind: {
			store: '{users}'
		}
	} ]

});