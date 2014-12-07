Ext.define("Phoenix.model.Scenario", {
	extend: "Phoenix.model.ScenarioBase",

	/**
	 * Returns true if this {Phoenix.model.Scenario} has any invalid associated {Phoenix.model.ScenarioItem} models.
	 * 
	 * @return {Boolean}
	 */
	hasInvalidScenarioItems: function() {
		var result;
		result = false;
		this.scenarioItems().each(function(scenarioItem) {
			if (!scenarioItem.isValid()) {
				result = true;
				return false;
			}
		});
		return result;
	},

	/**
	 * Copies this {Phoenix.model.Scenario} and its set of {Phoenix.model.ScenarioItem}.
	 * 
	 * @return {Phoenix.model.Scenario} The copied Scenario.
	 */
	copy: function() {
		var newScenario;
		newScenario = this.callParent(arguments);
		newScenario.setId(null);
		newScenario.set("name", (newScenario.get('name')) + " (Copy)");
		this.scenarioItems().each(function(scenarioItem) {
			var newItem;
			newItem = scenarioItem.copy();
			newItem.setId(null);
			newScenario.scenarioItems().add(newItem);
			return true;
		});
		return newScenario;
	},

	/**
	 * Simulates a process that would normally happen on the server. Creates mock disaster plan analysis values for this {Phoenix.model.Scenario}.
	 */
	simulateServerCostBenefitAnalysis: function() {
		var planEffectivenessOptions;
		this.set("planCost", Ext.Number.randomInt(10, 50));
		this.set("impactCost", Ext.Number.randomInt(800, 5000));
		this.set("impactLength", Ext.Number.randomInt(4, 20));
		this.set("totalImpact", Ext.Number.randomInt(6000, 80000));
		planEffectivenessOptions = [ "Terrible", "Poor", "Marginal", "Good", "Great", "Excellent" ];
		return this.set("planEffectiveness", planEffectivenessOptions[Ext.Number.randomInt(0, 5)]);
	}
});