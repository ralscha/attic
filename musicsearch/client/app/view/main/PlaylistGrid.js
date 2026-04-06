Ext.define('MusicSearch.view.PlaylistGrid', {
	extend: 'Ext.grid.Panel',
	requires: ['Ext.form.Label'],
	multiSelect: true,
	sortableColumns: false,

	reference: 'playlistGrid',

	viewConfig: {
		getRowClass(rec, idx, rowPrms, ds) {
			return rec.get('playing') ? 'playlist-playing-row' : '';
		},
		plugins: {
			ptype: 'gridviewdragdrop',
			ddGroup: 'playlistGroup',
			dragText: 'Drag and drop to reorganize'
		}
	},

	bind: {
		store: '{playlist}',
		title: '{nowPlaying}',
		selection: '{selectedPlaylistSong}'
	},

	listeners: {
		itemdblclick: 'onPlaylistItemDblClick'
	},

	columns: {
		defaults: {
			hideable: false
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
			xtype: 'button',
			tooltip: 'Play',
			disabled: true,
			handler: 'onPlayButtonClick',
			iconCls: 'x-fa fa-play',
			bind: {
				disabled: '{!hasPlaylist}'
			}
		}, {
			xtype: 'button',
			tooltip: 'Pause',
			reference: 'pauseButton',
			handler: 'onPauseButtonClick',
			disabled: true,
			enableToggle: true,
			iconCls: 'x-fa fa-pause',
			bind: {
				disabled: '{!playingSound}'
			}
		}, {
			xtype: 'button',
			tooltip: 'Stop',
			disabled: true,
			handler: 'onStopButtonClick',
			iconCls: 'x-fa fa-stop',
			bind: {
				disabled: '{!playingSound}'
			}
		}, {
			xtype: 'button',
			tooltip: 'Previous',
			disabled: true,
			handler: 'onPrevButtonClick',
			iconCls: 'x-fa fa-backward',
			bind: {
				disabled: '{!showPrevButton}'
			}
		}, {
			xtype: 'button',
			tooltip: 'Next',
			disabled: true,
			handler: 'onNextButtonClick',
			iconCls: 'x-fa fa-forward',
			bind: {
				disabled: '{!showNextButton}'
			}
		}, '-', {
			xtype: 'button',
			tooltip: 'Remove from playlist',
			disabled: true,
			handler: 'onRemoveButtonClick',
			iconCls: 'x-fa fa-minus',
			bind: {
				disabled: '{!selectedPlaylistSong}'
			}
		}, {
			xtype: 'button',
			tooltip: 'Clear playlist',
			disabled: true,
			iconCls: 'x-fa fa-trash',
			handler: 'onClearPlaylistButtonClick',
			bind: {
				disabled: '{!hasPlaylist}'
			}
		}, '-', {
			xtype: 'slider',
			disabled: true,
			decimalPrecision: 1,
			width: 250,
			useTips: false,
			reference: 'progressSlider',
			publishes: 'value',
			publishOnComplete: false,
			bind: {
				disabled: '{!playingSound}'
			},
			listeners: {
				dragstart: 'onProgressSliderDragStart',
				changecomplete: 'onProgressSliderChangeComplete'
			}
		}, ' ', {
			xtype: 'tbtext',
			bind: {
				text: '{progressText}'
			}
		} ]
	}, {
		xtype: 'toolbar',
		dock: 'right',
		items: [ {
			xtype: 'label',
			html: '<span class="x-fa fa-volume-up"></span>'
		}, {
			xtype: 'slider',
			reference: 'volumeSlider',
			maxValue: 100,
			value: 50,
			height: 160,
			vertical: true,
			listeners: {
				change: 'onVolumeSliderChange'
			}
		}, {
			xtype: 'label',
			html: '<span class="x-fa fa-volume-off"></span>'
		} ]

	} ]

});