Ext.define('Addressbook.store.Contacts', {
	extend: 'Ext.data.Store',
	requires: [ 'Addressbook.model.Contact' ],

	model: 'Addressbook.model.Contact',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	autoSync: false,
	sorters: [ {
		property: 'lastName',
		direction: 'ASC'
	} ]
});