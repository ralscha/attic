Ext.define('Changelog.store.BuildCustomers', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.Customer',
	autoLoad: true,
	remoteSort: false,
	pageSize: 999999,
	sorters: [ {
		property: 'shortName',
		direction: 'ASC'
	}]
});