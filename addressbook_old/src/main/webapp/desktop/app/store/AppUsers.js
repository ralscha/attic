Ext.define('Addressbook.store.AppUsers', {
	extend: 'Ext.data.Store',
	requires: [ 'Addressbook.model.AppUser' ],

	model: 'Addressbook.model.AppUser',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	autoSync: false,
	sorters: [ {
		property: 'name',
		direction: 'ASC'
	} ]
});