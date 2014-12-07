Ext.define('CarTracker.view.report.make.Chart', {
	extend: 'Ext.chart.Chart',
	alias: 'widget.report.make.chart',
	requires: [ 'Ext.chart.series.Pie', 'Ext.data.JsonStore', 'CarTracker.ux.chart.SmartLegend' ],
	store: Ext.create('Ext.data.JsonStore', {
		fields: [ 'name', 'totalSales' ]
	}),
	cls: 'x-panel-body-default',
	animate: true,
	border: false,
	shadow: true,
	
	legend: Ext.create('CarTracker.ux.chart.SmartLegend', {
        position:       'bottom',
        rebuild:        true,
        boxStrokeWidth: 1
    }),
	
	insetPadding: 60,
	theme: 'Base:gradients',
	series: [ {
		type: 'pie',
		field: 'totalSales',
		showInLegend: true,
		donut: 10,
		tips: {
			trackMouse: true,
			width: 170,
			height: 28,
			renderer: function(storeItem, item) {
				// calculate percentage.
				var total = 0;
				storeItem.store.each(function(rec) {
					total += rec.get('totalSales');
				});
				this.setTitle(storeItem.get('name') + ': ' + Math.round(storeItem.get('totalSales') / total * 100) + '% ('
						+ Ext.util.Format.usMoney(storeItem.get('totalSales')) + ')');
			}
		},
		highlight: {
			segment: {
				margin: 20
			}
		},
		label: {
			field: 'name',
			display: 'rotate',
			contrast: true,
			font: '18px Arial'
		}
	} ]
});