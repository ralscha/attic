Ext.define('Proto.store.ClientState', {
	extend: 'Ext.data.Store',
	requires: [ 'Proto.model.ClientState' ],
	model: 'Proto.model.ClientState',
	autoLoad: false,
	remoteFilter: false,
	remoteSort: false,
	pageSize: 0,
	proxy: {
		type: 'direct',
		api: {
			read: 'clientStateService.read',
			create: 'clientStateService.create',
			update: 'clientStateService.update',
			destroy: 'clientStateService.destroy'
		},
		writer: {
			writeAllFields: true
		}
	}
});