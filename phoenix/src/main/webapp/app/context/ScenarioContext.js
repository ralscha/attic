/**
 * Fire events related to the scenario UI, which other objects can respond to.
 */
Ext.define("Phoenix.context.ScenarioContext", {
	extend: "Phoenix.context.AbstractContext",

	/**
	 * Constructor.
	 */
	constructor: function(config) {
		if (config === null) {
			config = {};
		}
		this.callParent(arguments);
		return this.addEvents("initialDataLoaded", "scenarioOpened", "scenarioDeleted");
	},

	/**
	 * Notifies interested objects that initial data has been loaded.
	 */
	initialDataLoaded: function() {
		/**
		 * @event initialDataLoaded Initial data loaded.
		 */
		return this.fireEvent("initialDataLoaded");
	},

	/**
	 * Notified interested objects that a scenario is being opened.
	 * 
	 * @param {Phoenix.model.Scenario}
	 *            Scenario The Scenario being opened.
	 */
	scenarioOpened: function(scenario) {
		/**
		 * @event scenarioOpened Scenario opened.
		 * @param {Phoenix.model.Scenario}
		 *            Scenario The Scenario being opened.
		 */
		return this.fireEvent("scenarioOpened", scenario);
	},

	/**
	 * Notified interested objects that a scenario is being deleted.
	 * 
	 * @param {Phoenix.model.Scenario}
	 *            Scenario The Scenario being deleted.
	 */
	scenarioDeleted: function(scenario) {
		/**
		 * @event scenarioDeleted Scenario deleted.
		 * @param {Phoenix.model.Scenario}
		 *            Scenario The Scenario being deleted.
		 */
		return this.fireEvent("scenarioDeleted", scenario);
	}
});
