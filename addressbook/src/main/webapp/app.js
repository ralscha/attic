/* <debug> */
Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Ab': 'app',
		'Ext.ux': 'resources/extjs-gpl/4.2.2/ux'
	}
});
/* </debug> */

Ext.define('Ab.App', {
	extend: 'Deft.mvc.Application',
	requires: [ 'overrides.AbstractMixedCollection', 'Ab.ux.window.Notification', 'Ab.view.Viewport', 'Ab.store.Roles' ],

	init: function() {
		Ext.fly('circularG').destroy();
		Ext.setGlyphFontFamily('custom');
		Ext.tip.QuickTipManager.init();

		Ext.direct.Manager.addProvider(REMOTING_API);

		if (Ext.view.AbstractView) {
			Ext.view.AbstractView.prototype.loadingText = i18n.loading;
		}

		this.setupGlobalErrorHandler();

		Ext.direct.Manager.on('event', function(e) {
			if (e.code && e.code === 'parse') {
				window.location.reload();
			}
		});

		Deft.Injector.configure(this.buildInjectorConfiguration());
		return Ext.create("Ab.view.Viewport");

	},

	buildInjectorConfiguration: function() {
		var config = {
			messageBus: 'Ext.util.Observable',
			navigationStore: 'Ab.store.Navigation',
			contactGroupsStore: 'Ab.store.ContactGroups',
			rolesStore: 'Ab.store.Roles'
		};

		return config;
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
	Ext.create('Ab.App');
});