Ext.define('Proto.view.user.WindowController', {
	extend: 'Proto.base.WindowController',
	
	getStoreName: function() {
		return 'users';
	},
	
	getSelectedViewModelName: function() {
		return 'selectedUser';
	}

});
