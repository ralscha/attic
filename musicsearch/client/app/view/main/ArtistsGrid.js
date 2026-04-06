Ext.define('MusicSearch.view.ArtistsGrid', {
	extend: 'Ext.grid.Panel',
	title: 'Artists',

	bind: {
		store: '{artists}'
	},

	reference: 'artistsGrid',

	listeners: {
		itemdblclick: 'onArtistDblClick'
	},

	features: [ {
		ftype: 'grouping',
		groupHeaderTpl: '{name}',
		startCollapsed: true
	} ],

	columns: [ {
		dataIndex: 'name',
		text: 'Name',
		hideable: false,
		flex: 1
	} ]

});