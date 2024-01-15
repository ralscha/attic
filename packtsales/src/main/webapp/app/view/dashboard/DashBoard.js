Ext.define('Sales.view.dashboard.DashBoard', {
	extend: 'Sales.panel.Screen',
	alias: 'widget.myapp-dashboard',
	itemId: 'screen-dashboard',
	requires: [ 'Sales.view.dashboard.Pie', 'Sales.view.dashboard.Bar', 'Sales.view.dashboard.Line', 'Sales.view.dashboard.Radar' ],
	title: 'Dashboard',
	layout: {
		type: 'hbox',
		pack: 'start',
		align: 'stretch'
	},
	items: [ {
		xtype: 'container',
		flex: 1,
		padding: '20 10 20 20',
		layout: {
			type: 'vbox',
			pack: 'start',
			align: 'stretch'
		},
		items: [ {
			flex: 1,
			padding: '0 0 10 0',
			xtype: 'myapp-dashboard-pie'
		}, {
			flex: 1,
			padding: '10 0 0 0',
			xtype: 'myapp-dashboard-bar'
		} ]
	}, {
		xtype: 'container',
		flex: 1,
		padding: '20 20 20 10',
		layout: {
			type: 'vbox',
			pack: 'start',
			align: 'stretch'
		},
		items: [ {
			flex: 1,
			padding: '0 0 10 0',
			xtype: 'myapp-dashboard-line'
		}, {
			flex: 1,
			padding: '10 0 0 0',
			xtype: 'myapp-dashboard-radar'
		} ]
	} ]
});
