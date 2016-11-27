Ext.define('Starter.view.user.WindowController', {
	extend: 'Starter.base.WindowController',
	
	getStoreName: function() {
		return 'users';
	},
	
	getSelectedViewModelName: function() {
		return 'selectedUser';
	}

});
