Ext.define('Proto.view.project.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Proto.model.Project' ],

	data: {
		selectedProject: null
	},

	stores: {
		projects: {
			model: 'Proto.model.Project',
			autoLoad: true,
			remoteSort: false,
			remoteFilter: false,
			pageSize: 0,
			sorters: [ {
				property: 'name',
				direction: 'ASC'
			} ]
		}
	}

});