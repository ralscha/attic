Ext.define('Todo.view.todo.MainModel', {
	extend : 'Ext.app.ViewModel',
	requires : [ 'Ext.data.BufferedStore' ],

	data : {
		selectedTodo : null,
		searchButtonText: 'Search Title'
	},

	stores : {
		todos : {
			model : 'Todo.model.Todo',
			buffered : true,
			autoLoad : false,
			remoteSort : true,
			remoteFilter : true,
			pageSize : 25,
			leadingBufferZone : 200,
			sorters : [ {
				property : 'due',
				direction : 'ASC'
			} ]

		}
	},

	formulas : {}
});
