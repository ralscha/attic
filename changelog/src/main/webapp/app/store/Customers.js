Ext.define('Changelog.store.Customers', {
	extend: 'Ext.data.Store',
	model: 'Changelog.model.Customer',
	autoLoad: false,
	remoteSort: false,
	autoSync : true,
	pageSize: 99999,
	remoteFilter: true,
	sorters: [ {
		property: 'shortName',
		direction: 'ASC'
	} ]
});