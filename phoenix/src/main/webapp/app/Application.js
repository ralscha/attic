/* <debug> */
Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Ext.ux': 'resources/extjs-gpl/4.2.2/ux'
	}
});
/* </debug> */

/**
 * DeftJS Application class for the Phoenix application.
 */
Ext.define("Phoenix.Application", {
	extend: "Deft.mvc.Application",

	requires: [ "overrides.AbstractMixedCollection", "overrides.data.Model", "overrides.data.writer.Json", "Deft.mixin.Controllable", "Deft.mixin.Injectable", "Deft.promise.Deferred",
			"Phoenix.view.Viewport", "Phoenix.store.ScenarioStore", "Phoenix.service.ScenarioService", "Phoenix.context.ScenarioContext",
			"Phoenix.store.ProbabilityStore", "Phoenix.store.RevenueImpactStore", "Phoenix.store.AffectedItemStore", "Phoenix.service.NotificationService",
			"Ext.app.Application" ],

	/**
	 * init() runs when Ext.onReady() is called.
	 */
	init: function() {
		this.beforeInit();
		Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
		Deft.Injector.configure(this.buildInjectorConfiguration());
		return this.afterInit();
	},

	/**
	 * @protected Returns the configuration object to pass to Deft.Injector.configure(). Override in subclasses to alter the Injector configuration
	 *            before returning the config object.
	 * @return {Object} The Injector configuration object.
	 */
	buildInjectorConfiguration: function() {
		return {
			scenarioStore: "Phoenix.store.ScenarioStore",
			scenarioService: "Phoenix.service.ScenarioService",
			scenarioContext: "Phoenix.context.ScenarioContext",
			probabilityStore: "Phoenix.store.ProbabilityStore",
			revenueImpactStore: "Phoenix.store.RevenueImpactStore",
			affectedItemStore: "Phoenix.store.AffectedItemStore",
			notificationService: "Phoenix.service.NotificationService"
		};
	},

	/**
	 * @protected Runs at the start of the init() method. Override in subclasses if needed.
	 */
	beforeInit: function() {
	},

	/**
	 * @protected Runs at the end of the init() method. Useful to create initial Viewport, start Jasmine tests, etc.
	 */
	afterInit: function() {
		Ext.tip.QuickTipManager.init();
		Ext.fly('circularG').destroy();
		return Ext.create("Phoenix.view.Viewport");
	}
});
