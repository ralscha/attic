Ext.define('MusicSearch.view.main.MainController', {
	extend: 'Ext.app.ViewController',

	init() {
		searchService.getInfo((result) => {
			const d = result.totalDuration;

			const days = Math.floor(d / (3600 * 24));
			const h = Math.floor(d % (3600 * 24) / 3600);
			const m = Math.floor(d % 3600 / 60);
			const s = Math.floor(d % 3600 % 60);

			let duration = days > 0 ? days + " Days " : "";
			duration += h > 0 ? h + " Hours " : "";
			duration += m > 0 ? m + " Minutes " : "";
			duration += s > 0 ? s + " Seconds" : "";

			this.getViewModel().set('searchResultTitle', 'MusicSearch: Total ' + result.noOfSongs + ' songs (' + duration + ')');
		});
	},

	onArtistDblClick(view, record) {
		const term = 'artist:"' + record.get('name') + '"';
		this.lookup('filterField').setValue(term);
		this.lookup('artistsGrid').collapse();
	},

	onSelectionChange() {
		const sm = this.lookup('searchResult').getSelectionModel();

		if (sm.hasSelection()) {
			const selectedRecords = sm.getSelection();
			const ids = Ext.Array.map(selectedRecords, (item) => item.getId());
			this.getViewModel().set('selectedSongsId', ids.join(','));
		}
		else {
			this.getViewModel().set('selectedSongsId', null);
		}
	},

	onSongDblClick(view, record) {
		const playlist = this.getStore('playlist');
		if (playlist.indexOf(record) === -1) {
			playlist.add(record);
		}
	},

	onFilter(cmp, newValue) {
		const val = Ext.isString(newValue) ? newValue : this.lookup('filterField').getValue();
		this.searchSongs(val);
	},

	onFilterClear(tf) {
		tf.setValue('');
	},

	searchSongs(term) {
		const vm = this.getViewModel();
		const filterValue = Ext.String.trim(term) || '';
		const songsStore = this.getStore('songs');
		vm.set('hasSongs', false);
		if (!Ext.isEmpty(filterValue)) {
			songsStore.clearFilter(true);
			songsStore.removeAll();
			songsStore.filter('filter', filterValue);
		}
		else {
			songsStore.removeAll();
			vm.set('searchInfo', '');
		}
	},

	onSongsLoaded(store, records) {
		const vm = this.getViewModel();

		let output;
		if (store.getCount() > 0) {
			output = 'Found ' + Ext.util.Format.plural(store.getCount(), 'song', 'songs');
			vm.set('hasSongs', true);
		}
		else {
			output = 'Nothing found';
			vm.set('hasSongs', false);
		}

		vm.set('searchInfo', output);
	},

	onAddSelectedButtonClick() {
		const playlist = this.getStore('playlist');
		const sm = this.lookup('searchResult').getSelectionModel();
		const insertRecords = [];

		Ext.Array.forEach(sm.getSelection(), (record) => {
			if (playlist.indexOf(record) === -1) {
				insertRecords.push(record);
			}
		});

		playlist.add(insertRecords);
		sm.deselectAll();
	},

	onAddAllButtonClick() {
		const insertRecords = [];
		const playlist = this.getStore('playlist');

		this.getStore('songs').each((record) => {
			if (playlist.indexOf(record) === -1) {
				insertRecords.push(record);
			}
		});

		playlist.add(insertRecords);
	},

	onPlaylistDatachanged(store) {
		this.getViewModel().set('hasPlaylist', store.getCount() > 0);
	},

	onPlaylistItemDblClick(view, record) {
		this.playSong(record);
	},

	onPlayButtonClick() {
		const selectedModels = this.lookup('playlistGrid').getSelectionModel().getSelection();
		if (selectedModels && selectedModels.length >= 1) {
			this.playSong(selectedModels[0]);
		}
		else {
			this.playSong(this.getStore('playlist').first());
		}
	},

	onPauseButtonClick(button) {
		const ps = this.getViewModel().get('playingSound');
		if (button.pressed) {
			if (ps) {
				ps.pause();
			}
		}
		else {
			if (ps) {
				ps.resume();
			}
		}
	},

	onStopButtonClick() {
		this.stopSong();
	},

	onPrevButtonClick() {
		const playlistStore = this.getStore('playlist');
		const songIndex = this.getViewModel().get('playingSound').songIndex;
		if (songIndex !== -1) {
			if (songIndex > 0) {
				this.playSong(playlistStore.getAt(songIndex - 1));
			}
		}
	},

	onNextButtonClick() {
		const playlistStore = this.getStore('playlist');
		const songIndex = this.getViewModel().get('playingSound').songIndex;
		if (songIndex !== -1) {
			if (songIndex < playlistStore.getCount() - 1) {
				this.playSong(playlistStore.getAt(songIndex + 1));
			}
		}
	},

	onRemoveButtonClick() {
		const selectedModels = this.lookup('playlistGrid').getSelectionModel().getSelection();
		const playing = false;
		Ext.each(selectedModels, (item) => {
			if (item.get('playing')) {
				playing = true;
				return false;
			}
		});

		if (playing) {
			this.stopSong();
		}

		this.getStore('playlist').remove(selectedModels);
	},

	onClearPlaylistButtonClick() {
		this.stopSong();
		this.getStore('playlist').removeAll();
	},

	onVolumeSliderChange(slider, newValue) {
		const ps = this.getViewModel().get('playingSound');
		if (ps) {
			ps.setVolume(newValue);
		}
	},

	onProgressSliderDragStart() {
		this.getViewModel().set('progressSliderDragging', true);
	},

	onProgressSliderChangeComplete(slider, newValue) {
		this.getViewModel().set('progressSliderDragging', false);
		const ps = this.getViewModel().get('playingSound');
		if (ps) {
			ps.setPosition((newValue / 100) * ps.duration);
		}
	},

	updateProgress() {
		const slider = this.lookup('progressSlider');
		const ps = this.getViewModel().get('playingSound');
		if (ps) {
			const duration = ps.duration;

			if (duration > 0) {
				if (!slider.thumbs[0].dragging) {
					slider.setValue((ps.position / duration) * 100);
				}
			}
			else {
				slider.setValue(0);
			}
		}
		else {
			slider.setValue(0);
		}
	},

	playSong(song) {
		this.stopSong();

		const me = this;

		const playingSound = soundManager.createSound({
			id: 'currentSound',
			url: 'downloadMusic?docId=' + song.getId(),
			type: song.get('encoding'),
			autoLoad: true,
			autoPlay: true,
			volume: this.lookup('volumeSlider').getValue(),

			onstop() {
				me.onSoundManagerStop();
			},
			onfinish() {
				me.onSoundManagerFinish();
			},
			whileplaying() {
				me.updateProgress();
			}
		});

		let ix = -1;
		this.getStore('playlist').each((rec, i, len) => {
			if (rec.getId() === song.getId()) {
				rec.set('playing', true);
				ix = i;
			}
			else {
				rec.set('playing', false);
			}
		});

		this.lookup('playlistGrid').getSelectionModel().select(song);

		playingSound.song = song;
		playingSound.songIndex = ix;
		this.getViewModel().set('playingSound', playingSound);

		this.lookup('pauseButton').toggle(false, false);
	},

	stopSong() {
		const ps = this.getViewModel().get('playingSound');
		if (ps) {
			ps.song.set('playing', false);
			ps.stop();
			ps.destruct();
			this.getViewModel().set('playingSound', null);
		}
	},

	onSoundManagerStop() {
		this.lookup('pauseButton').toggle(false, false);

		const playingSound = this.getViewModel().get('playingSound');
		this.getViewModel().set('playingSound', null);

		this.lookup('progressSlider').setValue(0);

		if (playingSound) {
			playingSound.song.set('playing', false);
		}
		this.lookup('playlistGrid').getSelectionModel().deselectAll();
	},

	onSoundManagerFinish(controller, sound) {
		const playlistStore = this.getStore('playlist');
		this.lookup('progressSlider').setValue(0);

		const songIndex = this.getViewModel().get('playingSound').songIndex;
		const record = playlistStore.getAt(songIndex + 1);
		if (record) {
			this.playSong(record);
		}
		else {
			this.stopSong();
		}
	}
});
