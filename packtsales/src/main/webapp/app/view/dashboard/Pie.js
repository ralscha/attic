Ext.define('Sales.view.dashboard.Pie', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.myapp-dashboard-pie',
	title: 'Pie Chart',
	layout: 'fit',
	requires: [ 'Sales.store.Pie' ],
	initComponent: function() {
		var me = this, store;
		store = Ext.create('Sales.store.Pie');
		Ext.apply(me, {
			items: [ {
				xtype: 'chart',
				store: store,
				series: [ {
					type: 'pie',
					field: 'data',
					showInLegend: true,
					label: {
						field: 'name',
						display: 'rotate',
						contrast: true,
						font: '18px Arial'
					}
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});
