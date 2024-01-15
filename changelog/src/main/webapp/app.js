Ext.define('Changelog.App', {
	extend: 'Deft.mvc.Application',
	requires: [ 'overrides.AbstractMixedCollection', 'Changelog.ux.window.Notification', 'Changelog.view.Viewport', 'Changelog.store.Roles', 'Changelog.store.CustomersAll',
			'Changelog.view.navigation.Header', 'Changelog.view.navigation.SideBar', 'Changelog.store.Navigation' ],

	init: function() {
		Ext.fly('circularG').destroy();

		ZeroClipboard.config({
			moviePath: app_context_path + "/resources/ZeroClipboard.swf"
		});

		Ext.tip.QuickTipManager.init();

		if (this.hasLocalstorage()) {
			Ext.state.Manager.setProvider(Ext.create('Ext.state.LocalStorageProvider'));
		} else {
			Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
		}

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
				Changelog.ux.window.Notification.error(i18n.error, i18n.error_accessdenied);
			} else {
				Changelog.ux.window.Notification.error(i18n.error, e.message);
			}
		});

		Ext.apply(Ext.form.field.VTypes, {
			password: function(val, field) {
				if (field.initialPassField) {
					var pwd = field.up('form').down('#' + field.initialPassField);
					return (val === pwd.getValue());
				}
				return true;
			},

			passwordText: i18n.user_passworddonotmatch
		});

		Deft.Injector.configure({
			messageBus: 'Ext.util.Observable'
		});

		Ext.create('Changelog.store.Roles');
		Ext.create('Changelog.store.CustomersAll');

		Ext.create('Changelog.view.Viewport');
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
		systemLogService.error(message);
	},

	hasLocalstorage: function() {
		try {
			return !!localStorage.getItem;
		} catch (e) {
			return false;
		}
	}
});

Ext.onReady(function() {
	Ext.create('Changelog.App');
});