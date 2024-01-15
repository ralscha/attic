Ext.define('Sales.controller.Abstract', {
	extend: 'Ext.app.Controller',
	uses: [ 'Sales.util.History' ],
	aliasPrefix: 'myapp-',
	constructor: function() {
		var me = this, screenName = me.screenName;

		Ext.apply(me, {
			history: MyApp.util.History
		});
		if (screenName) {
			me.history.controllers[screenName] = me;
			me.baseUrl = '#!/' + screenName;
			me.aliasName = me.aliasPrefix + screenName;
		}
		me.callParent(arguments);
	},
	loadIndex: function(url) {
		var me = this, url = url || me.baseUrl;
		if (url.substr(0, 2) !== '#!') {
			url = me.baseUrl + '/' + url;
		}
		me.history.push(url, {
			navigate: true,
			callback: function() {
				var self = Ext.ComponentQuery.query(me.aliasName)[0];
				self.fireEvent('myapp-show', self);
			}
		});
	},
	init: function() {
		var me = this, o = {};
		o[me.aliasName] = {
			'myapp-show': me.onShow,
			'myapp-hide': me.onHide
		};
		me.control(o);
	},
	setChangeFieldEvents: function(fields, xtype, fn, scope) {
		var me = this, format = Ext.String.format;
		Ext.iterate(fields, function(type, fs) {
			if (Ext.isString(fs)) {
				fs = [ fs ];
			}
			if (Ext.isArray(fs)) {
				Ext.iterate(fs, function(fname) {
					var o = {}, key;
					key = format('{0} {1}[name="{2}"]', xtype, type, fname);
					o[key] = {
						change: {
							fn: fn,
							scope: scope
						}
					};
					me.control(o);
				});
			}
		});
	},
	onShow: function() {
		// Nothing todo
	},
	onHide: function() {
		// Nothing todo
	}
});
