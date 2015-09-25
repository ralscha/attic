/* <debug> */
Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'${jsAppNamespace}': 'app',
		'Ext.ux': 'resources/extjs/4.2.2/ux'
	}
});
/* </debug> */

Ext.define('${jsAppNamespace}.App', {
	extend: 'Deft.mvc.Application',
	requires: [ 'overrides.AbstractMixedCollection', '${jsAppNamespace}.ux.window.Notification', '${jsAppNamespace}.view.Viewport', '${jsAppNamespace}.store.Roles' ],

	init: function() {
		Ext.fly('circularG').destroy();
		Ext.setGlyphFontFamily('custom');
		Ext.tip.QuickTipManager.init();

		var chartdatapoller = new Ext.direct.PollingProvider({
			id: 'chartdatapoller',
			type: 'polling',
			interval: 2000,
			url: Ext.app.POLLING_URLS.chartdata
		});
		var heartbeat = new Ext.direct.PollingProvider({
			type: 'polling',
			interval: 5 * 60 * 1000, // 5 minutes
			url: Ext.app.POLLING_URLS.heartbeat
		});
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API, chartdatapoller, heartbeat);
		Ext.direct.Manager.getProvider('chartdatapoller').disconnect();

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
				${jsAppNamespace}.ux.window.Notification.error(i18n.error, i18n.error_accessdenied);
			} else {
				${jsAppNamespace}.ux.window.Notification.error(i18n.error, e.message);
			}
		});

		Deft.Injector.configure({
			messageBus: 'Ext.util.Observable',
			rolesStore: {
				className: '${jsAppNamespace}.store.Roles',
				eager: true
			}
		});

		Ext.create('${jsAppNamespace}.view.Viewport');
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
	Ext.create('${jsAppNamespace}.App');
});