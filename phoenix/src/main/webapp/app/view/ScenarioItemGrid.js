/**
 * Grid view for {Phoenix.model.ScenarioItem} models.
 */
Ext.define("Phoenix.view.ScenarioItemGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.phoenix-view-scenarioItemGrid",
	requires: [ "Ext.grid.plugin.CellEditing", "Ext.selection.CellModel" ],
	inject: [ "affectedItemStore", "probabilityStore", "revenueImpactStore" ],

	config: {
		affectedItemStore: null,
		probabilityStore: null,
		revenueImpactStore: null
	},

	initComponent: function() {

		this.columns = [ {
			header: "Affected Item",
			dataIndex: "affectedItemId",
			width: 225,
			renderer: function(value, metaData, record, row, col, store, gridView) {
				var ref = this.getAffectedItemStore().getById(value);
				return ref !== null ? ref.get("value") : void 0;
			},
			editor: {
				xtype: "combobox",
				store: this.getAffectedItemStore(),
				queryMode: "local",
				displayField: "value",
				valueField: "id",
				emptyText: "Select Item",
				forceSelection: true,
				cls: "editorWithMargin"
			}
		}, {
			header: "Recovery Plan",
			dataIndex: "itemDescription",
			flex: 2,
			editor: {
				emptyText: "Enter Recovery Plan",
				cls: "editorWithMargin"
			}
		}, {
			xtype: "numbercolumn",
			header: "Recovery Time (days)",
			dataIndex: "timeToRecover",
			width: 155,
			editor: {
				allowBlank: false,
				cls: "editorWithMargin",
				xtype: "numberfield",
				hideTrigger: true,
				minLength: 1,
				minValue: 0
			}
		}, {
			xtype: "numbercolumn",
			header: "Plan Cost / Day",
			dataIndex: "cost",
			width: 150,
			renderer: "usMoney",
			editor: {
				allowBlank: false,
				cls: "editorWithMargin",
				xtype: "numberfield",
				hideTrigger: true,
				minLength: 1,
				minValue: 0
			}
		}, {
			header: "Impact on Revenue",
			dataIndex: "revenueImpactId",
			width: 150,
			renderer: function(value, metaData, record, row, col, store, gridView) {
				var ref = this.getRevenueImpactStore().getById(value);
				return ref !== null ? ref.get("value") : void 0;
			},
			editor: {
				xtype: "combobox",
				store: this.getRevenueImpactStore(),
				queryMode: "local",
				displayField: "value",
				valueField: "id",
				emptyText: "Select Severity",
				forceSelection: true,
				cls: "editorWithMargin"
			}
		}, {
			xtype: "actioncolumn",
			itemId: "scenarioItemActionColumn",
			text: "Delete",
			width: 60,
			align: "center",
			sortable: false,
			items: [ {
				itemId: "scenarioItemDeleteButton",
				tooltip: "Delete Scenario Item",
				iconCls: "delete-icon mousepointer x-grid-center-icon"
			} ]
		} ];

		this.columnLines = true;
		
		this.selModel = Ext.create("Ext.selection.CellModel", {
			enableKeyNav: true
		});

		this.plugins = [{
	        ptype: 'cellediting',
	        clicksToEdit: 1,
	        pluginId: 'cellplugin'
	    }];

		this.viewConfig = {
			stripeRows: true,
			emptyText: "<div class='x-grid-empty-custom'>There are no Scenario Items defined yet.</div>",
			deferEmptyText: false
		};
		this.tbar = [ {
			text: "Add Scenario Item",
			itemId: "addButton",
			iconCls: "add-icon"
		} ];

		return this.callParent(arguments);
	}
});
