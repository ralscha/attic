Ext.define('Packt.view.reports.SalesFilmCategoryColumn', {
	extend: 'Ext.chart.Chart',
	alias: 'widget.salesfilmcategorycol',

	animate: true,
	store: 'reports.SalesFilmCategory',
	shadow: true,
	insetPadding: 60,
	theme: 'Base:gradients',
	axes: [ {
		type: 'Numeric',
		position: 'left',
		fields: [ 'totalSales' ],
		label: {
			renderer: Ext.util.Format.numberRenderer('0,0')
		},
		title: 'Total Sales',
		grid: true,
		minimum: 0
	}, {
		type: 'Category',
		position: 'bottom',
		fields: [ 'category' ],
		title: 'Film Category'
	} ],
	series: [ {
		type: 'column',
		axis: 'left',
		highlight: true,
		tips: {
			trackMouse: true,
			width: 140,
			height: 28,
			renderer: function(storeItem, item) {
				this.setTitle(storeItem.get('category') + ': ' + storeItem.get('totalSales') + ' $');
			}
		},
		label: {
			display: 'insideEnd',
			'text-anchor': 'middle',
			field: 'totalSales',
			renderer: Ext.util.Format.numberRenderer('0'),
			orientation: 'vertical',
			color: '#333'
		},
		xField: 'category',
		yField: 'totalSales'
	} ]
});