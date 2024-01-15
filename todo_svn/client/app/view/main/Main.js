Ext.define('Todo.view.main.Main', {
	extend: 'Ext.tab.Panel',
	requires: [ 'Ext.plugin.Viewport' ],

	controller: {
		xclass: 'Todo.view.main.MainController'
	},
	viewModel: {
		xclass: 'Todo.view.main.MainModel'
	},
		
	listeners: {
		beforetabchange: 'onBeforeTabChange'
	},

	items: [ {
		xclass: 'Todo.view.todo.Todo'
	}, {
		xclass: 'Todo.view.changes.Changes'
	} ]
});
