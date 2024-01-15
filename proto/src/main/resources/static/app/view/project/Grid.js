Ext.define('Proto.view.project.Grid', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Proto.view.project.Controller', 'Proto.view.project.ViewModel' ],

	title: 'Projects',
	closable: true,

	controller: {
		xclass: 'Proto.view.project.Controller'
	},

	viewModel: {
		xclass: 'Proto.view.project.ViewModel'
	},

	bind: {
		store: '{projects}',
		selection: '{selectedProject}'
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
		text: 'Name',
		dataIndex: 'name',
		flex: 1
	}],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.create,
			glyph: 0xe807,
			handler: 'newProject'
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