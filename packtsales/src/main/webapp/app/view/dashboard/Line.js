Ext.define('Sales.view.dashboard.Line', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.myapp-dashboard-line',
	title: 'Line Chart',
	layout: 'fit',
	requires: [ 'Sales.store.Line' ],
	initComponent: function() {
		var me = this, store;
		store = Ext.create('Sales.store.Line');
		Ext.apply(me, {
			items: [ {
				xtype: 'chart',
				store: store,
				legend: {
					position: 'right'
				},
				axes: [ {
					type: 'Numeric',
					minimum: 0,
					position: 'left',
					fields: [ 'quotation', 'bill' ],
					title: 'Documents',
					minorTickSteps: 1
				}, {
					type: 'Category',
					position: 'bottom',
					fields: [ 'mon' ],
					title: 'Month of the Year'
				} ],
				series: [ {
					type: 'line',
					highlight: {
						size: 7,
						radius: 7
					},
					axis: 'left',
					xField: 'mon',
					yField: 'quotation'
				}, {
					type: 'line',
					highlight: {
						size: 7,
						radius: 7
					},
					axis: 'left',
					xField: 'mon',
					yField: 'bill'
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});
