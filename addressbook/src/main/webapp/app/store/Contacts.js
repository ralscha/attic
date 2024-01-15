Ext.define('Ab.store.Contacts', {
	extend: 'Ext.data.Store',
	requires: [ 'Ab.model.Contact' ],

	model: 'Ab.model.Contact',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	autoSync: false,
	sorters: [ {
		property: 'lastName',
		direction: 'ASC'
	} ]
});