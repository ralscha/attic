Ext.define('Sales.view.dashboard.Radar', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.myapp-dashboard-radar',
	title: 'Radar Chart',
	layout: 'fit',
	requires: [ 'Sales.store.Radar' ],
	initComponent: function() {
		var me = this, store;
		store = Ext.create('Sales.store.Radar');
		Ext.apply(me, {
			items: [ {
				xtype: 'chart',
				store: store,
				insetPadding: 20,
				legend: {
					position: 'right'
				},
				axes: [ {
					type: 'Radial',
					position: 'radial',
					label: {
						display: true
					}
				} ],
				series: [ {
					type: 'radar',
					xField: 'mon',
					yField: 'quotation',
					showInLegend: true,
					showMarkers: true,
					markerConfig: {
						radius: 5,
						size: 5
					},
					style: {
						'stroke-width': 2,
						fill: 'none'
					}
				}, {
					type: 'radar',
					xField: 'mon',
					yField: 'bill',
					showInLegend: true,
					showMarkers: true,
					markerConfig: {
						radius: 5,
						size: 5
					},
					style: {
						'stroke-width': 2,
						fill: 'none'
					}
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});
