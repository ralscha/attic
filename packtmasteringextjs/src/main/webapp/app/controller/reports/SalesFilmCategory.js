Ext.define('Packt.controller.reports.SalesFilmCategory', {
	extend: 'Ext.app.Controller',
	requires: [ 'Packt.view.reports.SalesFilmCategory', 'Packt.view.reports.SalesFilmCategoryBar', 'Packt.view.reports.SalesFilmCategoryColumn',
			'Packt.view.reports.SalesFilmCategoryPie', 'Packt.store.reports.SalesFilmCategory' ],
	views: [ 'reports.SalesFilmCategory', 'reports.SalesFilmCategoryPie', 'reports.SalesFilmCategoryColumn', 'reports.SalesFilmCategoryBar' ],

	stores: [ 'reports.SalesFilmCategory' ],

	init: function(application) {
		this.control({
			"salesfilmcategorypie": {
				render: this.render
			},
			"salesfilmcategory menu#changeType menuitem": {
				click: this.onChangeChart
			},
			"salesfilmcategory menu#download menuitem": {
				click: this.onChartDownload
			}
		});
	},

	render: function(component, options) {
		component.getStore().load();
	},

	onChangeChart: function(item, e, options) {
		var panel = item.up('salesfilmcategory');

		if (item.itemId === 'pie') {
			panel.getLayout().setActiveItem(0);
		} else if (item.itemId === 'column') {
			panel.getLayout().setActiveItem(1);
		} else if (item.itemId === 'bar') {
			panel.getLayout().setActiveItem(2);
		}
	},

	onChartDownload: function(item, e, options) {
		var chart = item.up('salesfilmcategory').getLayout().getActiveItem();

		if (item.itemId === 'png') {
			Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as Image?', function(choice) {
				if (choice === 'yes') {
					chart.save({
						type: 'image/png'
					});
				}
			});
		} else if (item.itemId === 'svg') {
			Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as SVG + XML?', function(choice) {
				if (choice === 'yes') {
					var svg = chart.save({
						type: 'image/svg+xml'
					});
					window.open("data:image/svg+xml," + encodeURIComponent(svg));
				}
			});
		} else if (item.itemId === 'pdf') {
			Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as PDF?', function(choice) {
				if (choice === 'yes') {
					chart.save({
						type: 'image/png',
						url: 'svg2png?pdf=true'
					});
				}
			});
		}
	}
});
