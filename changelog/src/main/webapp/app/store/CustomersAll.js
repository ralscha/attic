Ext.define('Changelog.store.CustomersAll', {
	extend: 'Ext.data.Store',
	storeId: 'customersAllStore',
	model: 'Changelog.model.Customer',
	autoLoad: true,
	remoteSort: false,
	pageSize: 999999,
	sorters: [ {
		property: 'shortName',
		direction: 'ASC'
	} ]
});