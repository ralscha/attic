Ext.define('Golb.view.post.ViewModel', {
	extend: 'Ext.app.ViewModel',

	data: {
		selectedObject: null,
		totalCount: null
	},

	stores: {
		objects: {
			model: 'Golb.model.Post',
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
		},
		tags: {
			source: 'tags',
			filters: [ {
				property: 'blog',
				value: 'true'
			} ]
		},
		tagsForm: {
			source: 'tags',
			filters: [ {
				property: 'blog',
				value: 'true'
			} ]
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