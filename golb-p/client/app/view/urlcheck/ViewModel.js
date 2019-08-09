Ext.define('Golb.view.urlcheck.ViewModel', {
	extend: 'Ext.app.ViewModel',

	stores: {
		objects: {
			model: 'Golb.model.UrlCheck',
			autoLoad: false,
			remoteSort: false,
			remoteFilter: false,
			sorters: [ {
				property: 'post',
				direction: 'ASC'
			} ],
			pageSize: 0
		}
	}

});