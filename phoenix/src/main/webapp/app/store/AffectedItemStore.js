Ext.define("Phoenix.store.AffectedItemStore", {
	extend: "Ext.data.Store",
	requires: [ "Phoenix.model.AffectedItem" ],
	model: "Phoenix.model.AffectedItem",
	autoLoad: false
});
