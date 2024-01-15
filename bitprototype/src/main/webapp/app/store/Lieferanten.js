Ext.define('BitP.store.Lieferanten', {
	extend: 'Ext.data.Store',
	model: 'BitP.model.Lieferant',
	alias: 'store.lieferanten',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	autoSync: false,
	sorters: [ {
		property: 'firma',
		direction: 'ASC'
	} ]
});