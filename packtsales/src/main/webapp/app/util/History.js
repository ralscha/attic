Ext.define('Sales.util.History', {
	singleton: true,
	uses: [ 'Ext.util.History' ],
	controllers: {},
	init: function(o) {
		var me = this, o = o || {};
		Ext.applyIf(this, o);
		Ext.applyIf(this, {
			screens: []
		});
		// screen name is sorted in long order
		me.screens.sort(function(a, b) {
			return (b.length - a.length);
		});
		// History processing initialization
		Ext.util.History.init(function() {
			this.historyLoaded = true;
			this.initNavigate();
		}, this);
		// Event Handler setting
		Ext.util.History.on('change', me.navigate, me);
	},
	initNavigate: function() {
		var me = this;
		if (me.historyLoaded) {
			me.navigate(Ext.util.History.getToken());
		}
	},
	navigate: function(c) {
		var me = this, find = false, d;
		if (me.noNavigate) {
			me.noNavigate = false;
			return;
		}
		d = me.parseToken(c);
		Ext.iterate(me.controllers, function(screens, controller) {
			if (screens === d.type) {
				find = controller;
				return false;
			}
		});
		if (find) {
			find.loadIndex(d.url);
		} else {
			if (!me.ct) {
				location.href = '#!/dashboard';
			}
		}
	},
	parseToken: function(d) {
		var c = d && d.match(new RegExp(Ext.String.format('!?(\/({0})(\/(.*))?)', this.screens.join('|'))));
		return c ? {
			type: c[2],
			url: '#!' + c[1]
		} : {};
	},
	push: function(d, c) {
		d = this.cleanUrl(d);
		if (!/^#!?/.test(d)) {
			d = '#!' + d;
		}
		if (c && c.navigate) {
			this.noNavigate = false;
		} else {
			this.noNavigate = true;
		}
		Ext.util.History.add(d);
		if (c && c.callback) {
			c.callback();
		}
	},
	cleanUrl: function(b) {
		return b.replace(/^[^#]+#/, '#');
	},
	back: function() {
		Ext.util.History.back();
	},
	location: function(url) {
		location.href = url;
	}
});
