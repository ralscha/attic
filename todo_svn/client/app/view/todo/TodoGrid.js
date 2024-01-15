Ext.define('Todo.view.todo.TodoGrid', {
	extend: 'Ext.grid.Panel',

	autoLoad: true,

	bind: {
		store: '{todos}',
		selection: '{selectedTodo}'
	},

	listeners: {
		itemclick: 'onItemclick',
		itemkeyup: 'onGridSpecialKey'
	},

	columns: [ {
		xtype: 'rownumberer',
		flex: 5
	}, {
		text: 'Type',
		dataIndex: 'type',
		flex: 10
	}, {
		text: 'Title',
		dataIndex: 'title',
		flex: 65,
		renderer: function(value) {
			if (value) {
				try {
					var j = JSON.parse(value);
					var userLang = (navigator.language) ? navigator.language : navigator.userLanguage;
					var sbstr= userLang.substring(0,2);
					if('en'===sbstr) {
						sbstr = 'gb';
					}
					return j[sbstr];
				} catch(e) {
					return value;
				}
			}
			return value;
		}
	}, {
		xtype: 'datecolumn',
		format: 'Y-m-d',
		text: 'Due',
		dataIndex: 'due',
		flex: 10
	}, {
		text: 'Thumb',
		dataIndex: 'thumbnail64',
		flex: 10,
		renderer: function(value) {
			if (value) {
				return '<img src="data:image/jpg;base64,' + value + '" width="50">';
			}
			return value;
		}
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: 'New',
			iconCls: 'x-fa fa-plus',
			handler: 'onNewClick'
		}, {
			text: 'Delete',
			iconCls: 'x-fa fa-trash',
			handler: 'onDeleteClick',
			bind: {
				disabled: '{!selectedTodo}'
			}
		}, {
			emptyText: 'Filter',
			xtype: 'textfield',
			width: 250,
			reference: 'filterTf',
			listeners: {
              specialkey: 'onSpecialKey'
            }
		},  {
			xtype: 'splitbutton',
			bind: {
				text: '{searchButtonText}'
			},
			iconCls: 'x-fa fa-search',
			handler: 'onSearchClick',
			menu: new Ext.menu.Menu({
				items: [ {
					text: 'Title',
					handler: 'onSearchTitle'
				}, {
					text: 'Description',
					handler: 'onSearchDescription'
				}, {
					text: 'Fulltext',
					handler: 'onSearchFulltext'
				} ]
			})
		}]
		
	} ]

});