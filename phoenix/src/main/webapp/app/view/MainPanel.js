/**
 * Main (root) UI container.
 */
Ext.define("Phoenix.view.MainPanel", {
	extend: "Ext.tab.Panel",
	alias: "widget.phoenix-view-mainPanel",
	requires: [ "Phoenix.controller.MainPanelController", "Phoenix.view.ScenarioGrid" ],
	controller: "Phoenix.controller.MainPanelController",
	header: false,
	plain: true,
	title: "PHOENIX Disaster Recovery Scenario Planner (DeftJS Example Application)",
	initComponent: function() {
		Ext.applyIf(this, {
			items: [ {
				xtype: "phoenix-view-scenarioGrid"
			} ]
		});
		return this.callParent(arguments);
	}
});
