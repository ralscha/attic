Ext.define('Addressbook.store.ContactInfos', {
	extend: 'Ext.data.Store',
	requires: [ 'Addressbook.model.ContactInfo' ],

	model: 'Addressbook.model.ContactInfo',
	autoLoad: false,
	remoteSort: false,
	remoteFilter: false,
	autoSync: false,
	sorters: [ {
		property: 'infoType',
		direction: 'ASC'
	} ]
});