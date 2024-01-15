Ext.define("Addressbook.Application", {
	extend: "Deft.mvc.Application",
	requires: [ 'overrides.AbstractMixedCollection', 'Deft.promise.Deferred', 'Addressbook.view.Viewport', 'Ext.app.Application', 'Ext.direct.*',
			'Addressbook.ux.window.Notification', 'Addressbook.ux.form.field.ClearCombo', 'Addressbook.ux.form.field.FilterField',
			'Addressbook.store.Navigation', 'Addressbook.store.ContactGroups' ],

	init: function() {

		Ext.direct.Manager.addProvider(REMOTING_API);

		Deft.Injector.configure(this.buildInjectorConfiguration());

		Ext.tip.QuickTipManager.init();
		return Ext.create("Addressbook.view.Viewport");
	},

	buildInjectorConfiguration: function() {
		var config = {
			messageBus: 'Ext.util.Observable',
			navigationStore: 'Addressbook.store.Navigation',
			contactGroupsStore: 'Addressbook.store.ContactGroups'
		};

		return config;
	}
});
