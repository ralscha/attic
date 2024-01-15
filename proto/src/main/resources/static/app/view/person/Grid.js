Ext.define('Proto.view.person.Grid', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Proto.view.person.Controller', 'Proto.view.person.ViewModel' ],

	title: 'Persons',
	closable: true,

	controller: {
		xclass: 'Proto.view.person.Controller'
	},

	viewModel: {
		xclass: 'Proto.view.person.ViewModel'
	},

	bind: {
		store: '{persons}',
		selection: '{selectedPerson}'
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
		text: 'Last Name',
		dataIndex: 'lastName',
		flex: 1
	}, {
		text: 'First Name',
		dataIndex: 'firstName',
		flex: 1
	}],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.create,
			glyph: 0xe807,
			handler: 'newPerson'
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