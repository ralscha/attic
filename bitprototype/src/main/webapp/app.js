/* <debug> */
Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'BitP': 'app',
		'Ext.ux': 'resources/extjs-gpl/4.2.2/ux'
	}
});
/* </debug> */

Ext.define('BitP.App', {
	extend: 'Deft.mvc.Application',
	requires: [ 'overrides.AbstractMixedCollection', 'BitP.ux.window.Notification', 'BitP.view.Viewport', 'BitP.store.Roles' ],

	init: function() {
		Ext.fly('circularG').destroy();
		Ext.setGlyphFontFamily('custom');
		Ext.tip.QuickTipManager.init();

		var heartbeat = new Ext.direct.PollingProvider({
			type: 'polling',
			interval: 5 * 60 * 1000, // 5 minutes
			url: POLLING_URLS.heartbeat
		});
		Ext.direct.Manager.addProvider(REMOTING_API, heartbeat);

		if (Ext.view.AbstractView) {
			Ext.view.AbstractView.prototype.loadingText = i18n.loading;
		}

		this.setupGlobalErrorHandler();

		Ext.direct.Manager.on('event', function(e) {
			if (e.code && e.code === 'parse') {
				window.location.reload();
			}
		});

		Ext.direct.Manager.on('exception', function(e) {
			if (e.message === 'accessdenied') {
				BitP.ux.window.Notification.error(i18n.error, i18n.error_accessdenied);
			} else {
				BitP.ux.window.Notification.error(i18n.error, e.message);
			}
		});

		Deft.Injector.configure({
			messageBus: 'Ext.util.Observable',
			rolesStore: {
				className: 'BitP.store.Roles',
				eager: true
			}
		});

		Ext.create('BitP.view.Viewport');
	},

	setupGlobalErrorHandler: function() {
		var existingFn = window.onerror;
		if (typeof existingFn === 'function') {
			window.onerror = Ext.Function.createSequence(existingFn, this.globalErrorHandler);
		} else {
			window.onerror = this.globalErrorHandler;
		}
	},

	globalErrorHandler: function(msg, url, line) {
		var message = msg + "-->" + url + "::" + line;
		logService.error(message);
	}
});

Ext.onReady(function() {
	Ext.create('BitP.App');
});