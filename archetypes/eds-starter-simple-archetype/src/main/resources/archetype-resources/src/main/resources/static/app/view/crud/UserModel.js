Ext.define('${jsAppNamespace}.view.crud.UserModel', {
	extend: 'Ext.app.ViewModel',

	requires: [ '${jsAppNamespace}.model.User' ],

	data: {
		selectedUser: null
	},

	stores: {
		users: {
			model: '${jsAppNamespace}.model.User',
			autoLoad: false,
			pageSize: 25,
			remoteSort: true,
			remoteFilter: true,
			autoSync: false,
			sorters: [ {
				property: 'lastName',
				direction: 'ASC'
			} ]
		}
	}

});