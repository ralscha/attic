Ext.define('Proto.view.user.Grid', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Proto.view.user.Controller', 'Proto.view.user.ViewModel' ],

	title: i18n.user_users,
	closable: true,

	controller: {
		xclass: 'Proto.view.user.Controller'
	},

	viewModel: {
		xclass: 'Proto.view.user.ViewModel'
	},

	bind: {
		store: '{users}',
		selection: '{selectedUser}'
	},

	listeners: {
		itemdblclick: 'onItemDoubleClick',
		itemcontextmenu: 'onItemContextMenu'
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
		text: i18n.user_enabled,
		dataIndex: 'enabled',
		width: 85,
		renderer: function(value) {
			if (value === true) {
				return i18n.yes;
			}
			return i18n.no;
		}
	}, {
		text: i18n.user_locked,
		dataIndex: 'lockedOutUntil',
		width: 95,
		renderer: function(value) {
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
	} ]

});