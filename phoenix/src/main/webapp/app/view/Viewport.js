/**
 * Viewport shell for the Phoenix application.
 */

Ext.define("Phoenix.view.Viewport", {
	extend: "Ext.container.Viewport",
	requires: [ "Phoenix.view.MainPanel" ],
	layout: {
		type: "vbox",
		align: "stretch"
	},
	initComponent: function() {
		Ext.applyIf(this, {
			items: [ {
				xtype: "container",
				layout: "hbox",
				height: 60,
				items: [ {
					xtype: "component",
					cls: "phoenix-logo",
					width: 120,
					height: 50
				}, {
					xtype: "component",
					html: "<strong>Phoenix</strong>: Disaster Recovery Scenario Tool",
					border: false,
					cls: "phoenix-header",
					height: 50
				}, {
					xtype: "component",
					html: "(A <strong><a href='http://deftjs.org' target='_blank' muse_scanned='true'>DeftJS</a></strong> Example Application)",
					border: false,
					cls: "phoenix-header-small",
					height: 50
				} ]
			}, {
				xtype: "phoenix-view-mainPanel",
				flex: 1
			} ]
		});
		return this.callParent(arguments);
	}
});
