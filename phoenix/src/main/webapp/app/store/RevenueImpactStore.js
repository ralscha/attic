Ext.define("Phoenix.store.RevenueImpactStore", {
	extend: "Ext.data.Store",
	requires: [ "Phoenix.model.RevenueImpact" ],
	model: "Phoenix.model.RevenueImpact",
	autoLoad: false
});
