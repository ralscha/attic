Ext.define('Proto.view.user.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Proto.model.User', 'Proto.model.Role' ],

	data: {
		selectedUser: null
	},

	stores: {
		users: {
			model: 'Proto.model.User',
			autoLoad: true,
			remoteSort: false,
			remoteFilter: false,
			pageSize: 0,
			sorters: [ {
				property: 'lastName',
				direction: 'ASC'
			} ]
		}
	}

});