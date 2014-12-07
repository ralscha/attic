/**
 * Collection of {Phoenix.model.Scenario} models.
 */
Ext.define("Phoenix.store.ScenarioStore", {
	extend: "Ext.data.Store",
	requires: [ "Phoenix.model.ScenarioItemBase", "Phoenix.model.ScenarioItem", "Phoenix.model.ScenarioBase", "Phoenix.model.Scenario" ],
	model: "Phoenix.model.Scenario",
	autoLoad: false,

	/**
	 * Returns true the store contains unsynced {Phoenix.model.Scenario} models.
	 * 
	 * @return {Boolean}
	 */
	isSyncNeeded: function() {
		var result;
		result = false;
		if (this.getNewRecords().length || this.getModifiedRecords().length || this.getRemovedRecords().length || this.getUpdatedRecords().length) {
			result = true;
		}
		return result;
	}
});
