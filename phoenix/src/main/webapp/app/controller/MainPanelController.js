/**
 * Controls the main (root) UI container for the application.
 */
Ext.define("Phoenix.controller.MainPanelController", {
	extend: "Phoenix.controller.AbstractPhoenixController",
	requires: [ "Phoenix.view.ScenarioForm" ],
	observe: {
		scenarioContext: {
			scenarioOpened: "onScenarioOpened",
			scenarioDeleted: "onScenarioDeleted"
		}
	},

	control: {
		view: {
			boxready: "loadInitialData"
		}
	},

	init: function() {
		return this.callParent(arguments);
	},

	/**
	 * Loads the initial reference dta.
	 */
	loadInitialData: function() {
		var me = this;
		this.getView().setLoading(true);
		return this.getScenarioService().loadInitialData().then({
			success: function() {
				return me.getScenarioContext().initialDataLoaded();
			},
			failure: function(errorMessage) {
				return me.getNotificationService().error("Error", errorMessage);
			}
		}).always(function() {
			return me.getView().setLoading(false);
		});
	},

	/**
	 * Responds when a {Phoenix.model.Scenario} view is opened.
	 * 
	 * @param {Phoenix.model.Scenario}
	 *            Scenario being opened.
	 */
	onScenarioOpened: function(scenario) {
		var existingScenarioPanel;
		existingScenarioPanel = this.findExistingTab(scenario);
		if ((existingScenarioPanel !== null)) {
			return existingScenarioPanel.show();
		}

		return this.getView().add({
			xtype: "phoenix-view-scenarioForm",
			tabId: "scenarioPanel_" + scenario.getId(),
			title: scenario.get("name"),
			closable: true,
			scenario: scenario
		}).show();
	},

	/**
	 * Responds when a {Phoenix.model.Scenario} is deleted.
	 * 
	 * @param {Phoenix.model.Scenario}
	 *            Scenario being deleted.
	 */
	onScenarioDeleted: function(scenario) {
		var existingScenarioPanel;
		existingScenarioPanel = this.findExistingTab(scenario);
		if (existingScenarioPanel !== null) {
			return existingScenarioPanel.close();
		}
	},

	/**
	 * Locates a view for the specified {Phoenix.model.Scenario}. If not view is open, returns null.
	 * 
	 * @param {Phoenix.model.Scenario}
	 *            Scenario to locate a view for.
	 * @return {Phoenix.view.ScenarioForm}
	 */
	findExistingTab: function(scenario) {
		return this.getView().child('panel[tabId="scenarioPanel_' + scenario.getId() + '"]');
	}
});
