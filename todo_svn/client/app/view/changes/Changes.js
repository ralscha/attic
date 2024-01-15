Ext.define('Todo.view.changes.Changes', {
	extend: 'Ext.grid.Panel',

	controller: {
		xclass: 'Todo.view.changes.ChangesController'
	},

	viewModel: {
		xclass: 'Todo.view.changes.ChangesModel'
	},
	
	listeners: {
		activate: 'onActivate',
		deactivate: 'onDeactivate'
	},
	
	autoLoad: true,
	
	bind: {
		store: '{changes}'
	},

	title: 'Changes',

	columns: [ {
		text: 'Timestamp',
		dataIndex: 'timestamp',
		renderer: function(value) {
			var d = new Date(value);
			return Ext.Date.format(d, 'Y-m-d H:i:s');
		},
		width: 200
	}, {
		text: 'Type',
		dataIndex: 'type',
		width: 100
	}, {
		text: 'Id',
		dataIndex: 'todoId',
		width: 70
	}, {
		text: 'Property Name',
		dataIndex: 'property',
		flex: 1
	}, {
		text: 'Old',
		dataIndex: 'left',
		flex: 1
	}, {
		text: 'New',
		dataIndex: 'right',
		flex: 1
	} ]
});