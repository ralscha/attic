Ext.define('Packt.view.staticData.Categories', {
	extend: 'Packt.view.staticData.AbstractGrid',
	alias: 'widget.categoriesgrid',
	requires: ['Packt.store.staticData.Categories'],
	store: 'staticData.Categories',

	columns: [ {
		text: 'Category Id',
		width: 100,
		dataIndex: 'id',
		filter: {
			type: 'numeric'
		}
	}, {
		text: 'Category Name',
		flex: 1,
		dataIndex: 'name',
		editor: {
			allowBlank: false,
			maxLength: 45
		},
		filter: {
			type: 'string'
		}
	} ]
});