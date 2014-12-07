/**
 * Form view for editing a {Phoenix.model.Scenario}.
 */

Ext.define("Phoenix.view.ScenarioForm", {
	extend: "Ext.form.Panel",
	requires: [ "Phoenix.controller.ScenarioFormController", "Phoenix.view.ScenarioItemGrid" ],
	alias: "widget.phoenix-view-scenarioForm",
	controller: "Phoenix.controller.ScenarioFormController",
	inject: [ "affectedItemStore", "probabilityStore" ],
	config: {
		scenario: null,
		affectedItemStore: null,
		probabilityStore: null,
		requiredStyle: "<span class='ux-required-field' data-qtip='Required'>*</span>"
	},
	layout: "anchor",
	anchor: "100% 100%",

	initComponent: function() {
		var me = this;
		Ext.apply(this, {
			fieldDefaults: {
				msgTarget: "side",
				width: 600,
				labelWidth: 175,
				labelAlign: "right"
			},
			items: [ {
				xtype: "fieldset",
				title: "Scenario Information",
				collapsible: false,
				layout: "anchor",
				margin: 20,
				items: [ {
					xtype: "textfield",
					name: "name",
					fieldLabel: "Scenario Name",
					allowBlank: false,
					afterLabelTextTpl: this.getRequiredStyle(),
					margin: "10 0 7 0"
				}, {
					xtype: "textarea",
					name: "description",
					fieldLabel: "Scenario Description",
					height: 100,
					grow: true
				}, {
					xtype: "combobox",
					name: "probabilityId",
					fieldLabel: "Probability",
					store: this.getProbabilityStore(),
					queryMode: "local",
					displayField: "value",
					valueField: "id",
					emptyText: "Select a Probability",
					allowBlank: false,
					forceSelection: true,
					afterLabelTextTpl: this.getRequiredStyle(),
					margin: "0 0 12 0"
				} ]
			}, {
				xtype: "fieldset",
				title: "Scenario Items",
				collapsible: false,
				margin: 20,
				minHeight: 250,
				layout: "anchor",
				items: [ {
					xtype: "phoenix-view-scenarioItemGrid",
					itemId: "scenarioItemGrid",
					store: this.getScenario().scenarioItems(),
					anchor: "100% 100%",
					margin: "5 5 12 5"
				} ]
			} ],
			tbar: [ {
				text: "Save Scenario",
				itemId: "saveButton",
				iconCls: "save-icon",
				formBind: true
			}, {
				text: "Copy Scenario",
				itemId: "copyButton",
				iconCls: "copy-icon",
				formBind: true
			}, {
				text: "Close",
				itemId: "cancelButton",
				iconCls: "cancel-icon",
				listeners: {
					click: function() {
						return me.close();
					}
				}
			} ]
		});
		return this.callParent(arguments);
	}
});
