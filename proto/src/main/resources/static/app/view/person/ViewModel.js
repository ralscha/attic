Ext.define('Proto.view.person.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Proto.model.Person' ],

	data: {
		selectedPerson: null
	},

	stores: {
		persons: {
			model: 'Proto.model.Person',
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