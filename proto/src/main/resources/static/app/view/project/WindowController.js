Ext.define('Proto.view.project.WindowController', {
	extend: 'Proto.base.WindowController',
	
	getStoreName: function() {
		return 'projects';
	},
	
	getSelectedViewModelName: function() {
		return 'selectedProject';
	}

});
