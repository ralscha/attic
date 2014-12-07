Ext.define("Phoenix.store.ProbabilityStore", {
	extend: "Ext.data.Store",
	requires: [ "Phoenix.model.Probability" ],
	model: "Phoenix.model.Probability",
	autoLoad: false
});
