Ext.define('Proto.view.person.WindowController', {
	extend: 'Proto.base.WindowController',
	
	getStoreName: function() {
		return 'persons';
	},
	
	getSelectedViewModelName: function() {
		return 'selectedPerson';
	}

});
