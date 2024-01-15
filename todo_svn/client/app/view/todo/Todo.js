Ext.define('Todo.view.todo.Todo', {
	extend: 'Ext.panel.Panel',

	controller: {
		xclass: 'Todo.view.todo.MainController'
	},

	viewModel: {
		xclass: 'Todo.view.todo.MainModel'
	},

	title: 'Todo',
	layout: 'hbox',

	items: [ {
		xclass: 'Todo.view.todo.TodoGrid',
		flex: 2,
		height: '100%',
		padding: 20
	}, {
		xclass: 'Todo.view.todo.TodoForm',
		flex: 1,
		height: '100%',
		padding: 20
	} ]
});