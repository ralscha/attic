Ext.define('Golb.view.feedback.ViewModel', {
	extend: 'Ext.app.ViewModel',

	data: {
		selectedObject: null,
		totalCount: null
	},

	stores: {
		objects: {
			model: 'Golb.model.Feedback',
			autoLoad: false,
			remoteSort: false,
			remoteFilter: false,
			sorters: [ {
				property: 'created',
				direction: 'DESC'
			} ],
			listeners: {
				load: 'onObjectStoreLoad',
				filterchange: 'onObjectStoreLoad',
				add: 'onObjectStoreLoad',
				remove: 'onObjectStoreLoad'
			},
			pageSize: 0
		}
	}

});