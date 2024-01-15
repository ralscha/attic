Ext.define('Starter.Application', {
	extend: 'Ext.app.Application',
	name: 'Starter',
	requires: [ 'Starter.overrides.AccessKeyConfig', 'Starter.ux.form.trigger.Clear', 'Starter.Util', 'Starter.RemoteStateProvider', 'Starter.store.Languages' ],

	constructor: function() {
		Ext.setGlyphFontFamily('custom');

		var heartbeat = new Ext.direct.PollingProvider({
			type: 'polling',
			interval: 5 * 60 * 1000, // 5 minutes
			url: Ext.app.POLLING_URLS.heartbeat
		});
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API, heartbeat);
		this.setupGlobalErrorHandler();

		Ext.direct.Manager.on('event', function(e) {
			if (e.code && e.code === 'parse') {
				window.location.reload();
			}
		});

		Ext.direct.Manager.on('exception', function(e) {
			if (e.message === 'accessdenied') {
				Starter.Util.errorToast(i18n.accessdenied);
			}
			else {
				Starter.Util.errorToast(e.message);
			}
		});

		// state
		Ext.state.Manager.setProvider(new Starter.RemoteStateProvider());

		new Starter.store.Languages();

		this.callParent(arguments);
	},

	launch: function() {
		Ext.fly('cssloader').destroy();
	},

	setupGlobalErrorHandler: function() {
		var existingFn = window.onerror;
		if (typeof existingFn === 'function') {
			window.onerror = Ext.Function.createSequence(existingFn, this.globalErrorHandler);
		}
		else {
			window.onerror = this.globalErrorHandler;
		}
	},

	globalErrorHandler: function(message, file, line, col, error) {
		logService.error(message + '-->' + file + '::' + line);
	}

});
