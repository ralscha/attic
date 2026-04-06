Ext.define('MusicSearch.view.SearchResultGrid', {
	extend: 'Ext.grid.Panel',
	title: 'Search Music',

	multiSelect: true,
	reference: 'searchResult',

	viewConfig: {
		plugins: {
			ptype: 'gridviewdragdrop',
			dragText: 'Drag {0} song{1} to the playlist',
			ddGroup: 'playlistGroup',
			enableDrop: false
		},
		copy: true
	},

	bind: {
		store: '{songs}',
		title: '{searchResultTitle}',
		selection: '{selectedSongs}'
	},

	listeners: {
		itemdblclick: 'onSongDblClick',
		selectionchange: 'onSelectionChange'
	},

	columns: {
		defaults: {
			hideable: false,
			menuDisabled: true
		},

		items: [ {
			dataIndex: 'title',
			text: 'Title',
			flex: 4
		}, {
			dataIndex: 'album',
			text: 'Album',
			flex: 2
		}, {
			dataIndex: 'artist',
			text: 'Artist',
			flex: 2
		}, {
			dataIndex: 'year',
			text: 'Year'
		}, {
			dataIndex: 'durationInSeconds',
			text: 'Duration',
			align: 'right',
			renderer: function(value) {
				return MusicSearch.Util.secondsToHms(value);
			}
		}, {
			xtype: 'numbercolumn',
			dataIndex: 'bitrate',
			text: 'Bitrate',
			align: 'right',
			format: '0'
		} ]
	},

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			xtype: 'textfield',
			emptyText: 'Enter filter',
			width: 500,
			reference: 'filterField',
			triggers: {
				search: {
					cls: 'x-form-search-trigger',
					handler: 'onFilter'
				},
				clear: {
					cls: 'x-form-clear-trigger',
					handler: 'onFilterClear'
				}
			},

			listeners: {
				change: {
					fn: 'onFilter',
					buffer: 500
				}
			}
		}, {
			xtype: 'tbtext',
			bind: {
				html: '{searchInfo}'
			}
		}, '->', {
			xtype: 'button',
			text: 'Add All to Playlist',
			iconCls: 'x-fa fa-plus',
			disabled: true,
			handler: 'onAddAllButtonClick',
			bind: {
				disabled: '{!hasSongs}'
			}
		}, {
			xtype: 'button',
			text: 'Add Selected to Playlist',
			iconCls: 'x-fa fa-plus',
			disabled: true,
			handler: 'onAddSelectedButtonClick',
			bind: {
				disabled: '{!selectedSongs}'
			}
		}, {
			xtype: 'button',
			text: 'Download Selected',
			disabled: true,
			iconCls: 'x-fa fa-download',
			href: 'downloadMusicZip',
			hrefTarget: '_self',
			bind: {
				disabled: '{!selectedSongs}',
				params: {
					sf: '{selectedSongsId}'
				}
			}
		} ]
	} ]
});