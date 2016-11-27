Ext.define('Starter.view.main.MainModel', {
	extend: 'Ext.app.ViewModel',

	stores: {
		navigationStore: {
			type: 'tree',
			autoLoad: false,
			nodeParam: 'id',
			root: {
				expanded: true
			},
			proxy: {
				type: 'direct',
				directFn: 'navigationService.getNavigation'
			},
			listeners: {
				load: 'onNavigationStoreLoad'
			}
		},

		logLevels: {
			type: 'array',
			fields: [ 'level' ],
			data: [ [ 'ERROR' ], [ 'WARN' ], [ 'INFO' ], [ 'DEBUG' ] ]
		}
	}

});