Ext.define('Ab.store.ContactGroups', {
	extend: 'Ext.data.Store',
	requires: [ 'Ab.model.ContactGroup' ],
	model: 'Ab.model.ContactGroup',

	remoteSort: false,
	remoteFilter: false,
	autoSync: false,
	autoLoad: false,
	
	sorters: [ {
		property: 'name',
		direction: 'ASC'
	} ]
});