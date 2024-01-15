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
		},
		roles: {
			model: 'Starter.model.Role',
			autoLoad: true,
			remoteFilter: false,
			remoteSort: false,
			pageSize: 0
		}
	},

	formulas: {
		newUser: function(get) {
			var su = get('selectedUser');
			return !su || su.getId() < 0;
		},
		selectedUserNotLocked: function(get) {
			var su = get('selectedUser');
			return !su || !get('selectedUser').data.lockedOutUntil;
		}
	}

});