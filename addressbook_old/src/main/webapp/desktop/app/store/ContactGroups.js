Ext.define('Addressbook.store.ContactGroups', {
	extend: 'Ext.data.Store',
	requires: [ 'Addressbook.model.ContactGroup' ],
	model: 'Addressbook.model.ContactGroup',

	remoteSort: false,
	remoteFilter: false,
	autoSync: false,
	autoLoad: false,
	
	sorters: [ {
		property: 'name',
		direction: 'ASC'
	} ]
});