Ext.define('Todo.view.changes.ChangesModel', {
	extend: 'Ext.app.ViewModel',

	stores: {
		changes: {
			autoLoad: false,
			pageSize: 0,
			proxy: {
				type: 'rest',
				url: 'audit/todo'
			}
		}
	}

});
