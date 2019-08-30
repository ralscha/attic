Ext.define('Starter.view.crud.UserModel', {
	extend: 'Ext.app.ViewModel',

	data: {
		selectedUser: null,
		nameFilter: null,
		departmentFilterCB: null
	},

	stores: {
		users: {
			model: 'Starter.model.User',
			autoLoad: true,
			pageSize: 25,
			remoteSort: true,
			remoteFilter: true,
			autoSync: false,
			sorters: [ {
				property: 'lastName',
				direction: 'ASC'
			} ],
			filters: [ {
				property: 'name',
				value: '{nameFilter}'
			}, {
				property: 'department',
				value: '{departmentFilterCB.value}'
			} ]
		}
	}

});