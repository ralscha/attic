Ext.define("Phoenix.view.ScenarioGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.phoenix-view-scenarioGrid",
	controller: "Phoenix.controller.ScenarioGridController",
	requires: [ "Phoenix.controller.ScenarioGridController", "Ext.grid.column.Number", "Ext.grid.column.Date", "Ext.grid.column.Action",
			"Ext.grid.plugin.BufferedRenderer" ],
	inject: [ "scenarioStore", "probabilityStore" ],
	config: {
		scenarioStore: null,
		probabilityStore: null
	},
	title: "Scenarios",

	initComponent: function() {
		var me = this;
		Ext.apply(this, {

			store: this.getScenarioStore(),
			columns: [ {
				header: "Scenario Name",
				dataIndex: "name",
				flex: 1,
				minWidth: 140
			}, {
				header: "Description",
				dataIndex: "description",
				flex: 1,
				defaultRenderer: function(value, metaData, record) {
					metaData.tdAttr = "data-qtip='" + value + "'";
					return value;
				}
			}, {
				header: "Probability",
				dataIndex: "probabilityId",
				width: 145,
				defaultRenderer: function(value, metaData, record, row, col, store, gridView) {
					var ref = me.getProbabilityStore().getById(value);
					return ref !== null ? ref.get("value") : void 0;
				}
			}, {
				xtype: "numbercolumn",
				header: "Plan Cost / Day",
				dataIndex: "planCost",
				width: 85,
				renderer: "usMoney"
			}, {
				xtype: "numbercolumn",
				header: "Impact Cost / Day",
				dataIndex: "impactCost",
				width: 100,
				renderer: "usMoney"
			}, {
				xtype: "numbercolumn",
				header: "Length (Days)",
				dataIndex: "impactLength",
				width: 80
			}, {
				xtype: "numbercolumn",
				header: "Total Impact Cost",
				dataIndex: "totalImpact",
				width: 105,
				renderer: "usMoney"
			}, {
				header: "Plan Effectiveness",
				dataIndex: "planEffectiveness",
				width: 105,
				renderer: function(value, metaData, record) {
					metaData.tdCls = value;
					return value;
				}
			}, {
				xtype: "datecolumn",
				text: "Updated",
				dataIndex: "dateUpdated",
				format: "m-d-Y g:i A",
				width: 110
			}, {
				xtype: "actioncolumn",
				itemId: "scenarioActionColumn",
				text: "Delete",
				width: 50,
				align: "center",
				sortable: false,
				items: [ {
					itemId: "scenarioDeleteButton",
					tooltip: "Delete Scenario",
					iconCls: "delete-icon mousepointer x-grid-center-icon"
				} ]
			} ],
			columnLines: true,
			viewConfig: {
				stripeRows: true,
				emptyText: "<div class='x-grid-empty-custom'>There are no Scenarios defined yet.</div>",
				deferEmptyText: false
			},
			tbar: [ {
				text: "New Scenario",
				itemId: "addButton",
				iconCls: "add-icon"
			}, {
				xtype: "component",
				html: " (Double click row to edit)"
			} ]
		});
		return this.callParent(arguments);
	}
});
