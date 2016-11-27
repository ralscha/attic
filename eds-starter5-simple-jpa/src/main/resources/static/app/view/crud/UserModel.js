Ext.define('SimpleApp.view.crud.UserModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'SimpleApp.model.User' ],

	data: {
		selectedUser: null,
		nameFilter: null,
		departmentFilterCB: null
	},

	stores: {
		users: {
			model: 'SimpleApp.model.User',
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