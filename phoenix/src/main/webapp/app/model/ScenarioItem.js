Ext.define("Phoenix.model.ScenarioItem", {
	extend: "Phoenix.model.ScenarioItemBase",

	setUpDefaults: function() {
		this.set("affectedItemId", 1);
		this.set("cost", 0);
		this.set("description", "");
		this.set("timeToRecover", 0);
		return this.set("revenueImpactId", 1);
	},
	
	/**
	 * Returns true if this {Phoenix.model.ScenarioItem} has valid values.
	 * 
	 * @return {Boolean}
	 */
	isValid: function() {
		var result;
		result = false;
		if (this.get("affectedItemId") > 0 && this.get("revenueImpactId") > 0 && (this.get("cost") !== null) && (this.get("timeToRecover") !== null)) {
			result = true;
		}
		return result;
	}
});