Ext.define('Golb.store.Tags', {
	extend: 'Ext.data.Store',
	requires: [ 'Ext.data.proxy.Direct' ],
	storeId: 'tags',
	autoLoad: false,
	pageSize: 0,
	sorters: [ {
		property: 'name',
		direction: 'ASC'
	} ],
	model: 'Golb.model.Tag',
	proxy: {
		type: 'direct',
		directFn: 'tagService.readForCombo'
	}
});
