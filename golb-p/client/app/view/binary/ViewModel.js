Ext.define('Golb.view.binary.ViewModel', {
	extend: 'Ext.app.ViewModel',

	data: {
		selectedObject: null,
		totalCount: null
	},

	stores: {
		objects: {
			model: 'Golb.model.Binary',
			autoLoad: false,
			remoteSort: false,
			remoteFilter: false,
			sorters: [ {
				property: 'name',
				direction: 'ASC'
			} ],
			listeners: {
				load: 'onObjectStoreLoad',
				filterchange: 'onObjectStoreLoad',
				add: 'onObjectStoreLoad',
				remove: 'onObjectStoreLoad'
			},
			pageSize: 0
		}
	},

	formulas: {
		isPhantomObject: {
			bind: {
				bindTo: '{selectedObject}',
				deep: true
			},
			get: function(selectedObject) {
				return !selectedObject || selectedObject.phantom;
			}
		}
	}

});