Ext.define('Starter.view.loggedin.Controller', {
	extend: 'Ext.app.ViewController',

	absession: null,
	isConnecting: false,

	onClose: function() {
		this.endSubscription();
	},
	onDeactivate: function() {
		this.endSubscription();
	},
	onActivate: function() {
		this.startSubscription();
	},

	init: function() {
		this.getStore('loggedInUsers').load();
		this.startSubscription();
	},

	startSubscription: function() {
		if (this.isConnecting || this.absession !== null) {
			return;
		}

		this.isConnecting = true;

		var path = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/') + 1) + 'wamp';
		ab._construct = function(url, protocols) {
			return new SockJS(url);
		};

		var me = this;
		var store = this.getStore('loggedInUsers');

		ab.connect(path, function(session) {
			me.absession = session;
			me.isConnecting = false;
			session.subscribe('/queue/login', function(topic, accessLog) {
				store.add(accessLog);
			});
			session.subscribe('/queue/logout', function(topic, accessLogId) {
				var index = store.findExact('id', accessLogId);
				if (index >= 0) {
					store.removeAt(index);
				}
			});
		}, function(code, reason) {
			me.absession = null;
		}, {
			skipSubprotocolCheck: true
		});
	},

	endSubscription: function() {
		if (this.absession !== null) {
			this.absession.unsubscribe('/queue/login');
			this.absession.unsubscribe('/queue/logout');
			this.absession.close();
			this.absession = null;
			this.isConnecting = false;
		}
	}

});
