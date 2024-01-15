Ext.define('Ab.store.ContactInfos', {
	extend: 'Ext.data.Store',
	requires: [ 'Ab.model.ContactInfo' ],

	model: 'Ab.model.ContactInfo',
	autoLoad: false,
	remoteSort: false,
	remoteFilter: false,
	autoSync: false,
	sorters: [ {
		property: 'infoType',
		direction: 'ASC'
	} ]
});