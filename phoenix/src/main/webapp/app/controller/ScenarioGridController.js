/**
 * Controls the scenario grid.
 */
Ext.define("Phoenix.controller.ScenarioGridController", {
	extend: "Phoenix.controller.AbstractPhoenixController",

	observe: {
		scenarioContext: {
			initialDataLoaded: "loadScenarios"
		}
	},

	control: {
		view: {
			itemdblclick: "onEditScenario"
		},
		addButton: {
			click: "onAddButtonClick"
		},
		scenarioActionColumn: {
			click: "onActionColumnClick"
		}
	},

	init: function() {
		return this.callParent(arguments);
	},

	/**
	 * Loads the set of {Phoenix.model.Scenario} models
	 */
	loadScenarios: function() {
		var me = this;
		this.getView().setLoading(true);
		return this.getScenarioService().loadScenarios().then({
			failure: function(errorMessage) {
				return me.getNotificationService().error("Error", errorMessage);
			}
		}).always(function() {
			return me.getView().setLoading(false);
		}).done();
	},

	/**
	 * Deletes the passed {Phoenix.model.Scenario}.
	 * 
	 * @param {Phoenix.model.Scenario}
	 *            Scenario to delete.
	 */
	deleteScenario: function(scenario) {
		var me = this;
		this.getView().setLoading(true);
		return this.getScenarioService().deleteScenario(scenario).then({
			success: function() {
				me.getScenarioContext().scenarioDeleted(scenario);
				return me.getNotificationService().success("Success", "The scenario was deleted successfully.");
			},
			failure: function() {
				return me.getNotificationService().error("Error", "Error deleting Scenario.");
			}
		}).always(function() {
			return me.getView().setLoading(false);
		}).done();
	},

	/**
	 * Handles a click on the add button and opens a new Scenario view.
	 */
	onAddButtonClick: function() {
		var scenario;
		scenario = Ext.create("Phoenix.model.Scenario", {
			name: "New Scenario"
		});

		return this.getScenarioContext().scenarioOpened(scenario);
	},

	/**
	 * Handles a request to edit a {Phoenix.model.Scenario} model.
	 */
	onEditScenario: function(grid, scenario, row, rowIndex, event) {
		return this.getScenarioContext().scenarioOpened(scenario);
	},

	/**
	 * Handles a click on the grid's action column.
	 */
	onActionColumnClick: function(view, cell, rowIndex, columnIndex, event, scenario, row) {
		return Ext.MessageBox.confirm("Confirm", "Are you sure you want to delete the this Scenario: " + (scenario.get("name")), function(button) {
			if (button === "yes") {
				return this.deleteScenario(scenario);
			}
		}, this);
	}
});
