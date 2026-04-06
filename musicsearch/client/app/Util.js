Ext.define('MusicSearch.Util', {
	singleton: true,

	secondsToHms(d) {
		if (d) {
			const no = Number(d);
			const h = Math.floor(no / 3600);
			const m = Math.floor(no % 3600 / 60);
			const s = Math.floor(no % 3600 % 60);
			return ((h > 0 ? h + ":" : "") + (m > 0 ? (h > 0 && m < 10 ? "0" : "") + m + ":" : "00:") + (s < 10 ? "0" : "") + s);
		}
		return '';
	}

});