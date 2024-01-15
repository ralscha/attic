Ext.define('Sales.view.Viewport', {
	renderTo: Ext.getBody(),
	extend: 'Ext.container.Viewport',
	requires: [ 'Ext.panel.Panel', 'Ext.layout.container.Border', 'Ext.layout.container.Card', 'Sales.view.Header', 'Sales.view.Navigation',
			'Sales.view.dashboard.DashBoard', 'Sales.view.myaccount.MyAccount', 'Sales.view.quotation.Quotation', 'Sales.view.bill.Bill' ],

	layout: {
		type: 'border',
		padding: 5
	},

	items: [ {
		region: 'north',
		xtype: 'myapp-header',
		height: 35,
		border: false
	}, {
		region: 'west',
		xtype: 'myapp-navigation',
		width: 250,
		collapsible: true,
		animCollapse: false,
		collapseMode: 'mini',
		split: true,
		minWidth: 180,
		maxWidth: 350,
		width: 250
	}, {
		region: 'center',
		layout: 'card',
		border: false,
		items: [ {
			xtype: 'myapp-dashboard'
		}, {
			xtype: 'myapp-myaccount'
		}, {
			xtype: 'myapp-quotation'
		}, {
			xtype: 'myapp-bill'
		} ]
	} ]
});
