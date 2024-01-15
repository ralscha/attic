Ext.define('Starter.view.user.Grid', {
	extend: 'Ext.grid.Panel',
	title: i18n.user_users,
	icon: 'resources/images/users.png',
	bind: {
		store: '{users}',
		selection: '{selectedUser}'
	},

	listeners: {
		itemdblclick: 'onItemclick'
	},

	viewConfig: {
		loadMask: false
	},

	columns: [ {
		text: i18n.user_email,
		dataIndex: 'email',
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
		text: 'Role',
		dataIndex: 'role',
		flex: 1
	}, {
		xtype: 'datecolumn',
		format: 'Y-m-d H:i:s',
		text: i18n.user_lastlogin,
		dataIndex: 'lastLogin',
		width: 170,
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
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.create,
			glyph: 0xe807,
			handler: 'newUser'
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