Ext.define('Starter.view.user.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Starter.model.User', 'Starter.model.Role' ],

	data: {
		selectedUser: null
	},

	stores: {
		users: {
			model: 'Starter.model.User',
			autoLoad: false,
			remoteSort: true,
			remoteFilter: true,
			pageSize: 30,
			sorters: [ {
				property: 'lastName',
				direction: 'ASC'
			} ]
		}
	}

});