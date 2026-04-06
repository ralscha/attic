Ext.require('Ext.chart.*');
Ext.require([ 'Ext.Window', 'Ext.fx.target.Sprite', 'Ext.layout.container.Fit', 'Ext.window.MessageBox' ]);

Ext.onReady(function() {
	store1.loadData(generateData(8));

	Ext.draw.engine.ImageExporter.defaultUrl = "svg2png";

	var chart = new Ext.chart.Chart({
		id: 'chartCmp',
		xtype: 'chart',
		style: 'background:#fff',
		animate: true,
		theme: 'Category1',
		store: store1,
		axes: [ {
			type: 'Numeric',
			position: 'left',
			fields: [ 'data1', 'data2', 'data3' ],
			title: 'Number of Hits',
			grid: true
		}, {
			type: 'Category',
			position: 'bottom',
			fields: [ 'name' ],
			title: 'Month of the Year'
		} ],
		series: [ {
			type: 'column',
			axis: 'left',
			xField: 'name',
			yField: 'data1',
			markerConfig: {
				type: 'cross',
				size: 3
			}
		}, {
			type: 'scatter',
			axis: 'left',
			xField: 'name',
			yField: 'data2',
			markerConfig: {
				type: 'circle',
				size: 5
			}
		}, {
			type: 'line',
			axis: 'left',
			smooth: true,
			fill: true,
			fillOpacity: 0.5,
			xField: 'name',
			yField: 'data3'
		} ]
	});

	new Ext.Window({
		width: 800,
		height: 600,
		minHeight: 400,
		minWidth: 550,
		hidden: false,
		maximizable: true,
		title: 'Mixed Charts',
		renderTo: Ext.getBody(),
		layout: 'fit',
		tbar: [ {
			text: 'Save Chart as JPEG',
			handler: function() {
				Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as a JPEG image?', function(choice) {
					if (choice == 'yes') {
						chart.save({
							type: 'image/jpeg'
						});
					}
				});
			}
		}, {
			text: 'Save Chart as PNG',
			handler: function() {
				Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as a PNG image?', function(choice) {
					if (choice == 'yes') {
						chart.save({
							type: 'image/png'
						});
					}
				});
			}		
		}, {
			text: 'Save Chart as PDF',
			handler: function() {
				Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as a PDF?', function(choice) {
					if (choice == 'yes') {
						console.log('save');
						chart.save({
							type: 'image/png',
							url: 'svg2png?pdf=true'
						});
					}
				});
			}		
		}, {
			text: 'Save Chart as SVG',
			handler: function() {
				Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as SVG + XML?', function(choice) {
					if (choice === 'yes') {
						var svg = chart.save({
							type: 'image/svg+xml'
						});
						window.open("data:image/svg+xml," + encodeURIComponent(svg));
					}
				});
			}				
		}, {
			text: 'Reload Data',
			handler: function() {
				store1.loadData(generateData(8));
			}
		}, {
			enableToggle: true,
			pressed: true,
			text: 'Animate',
			toggleHandler: function(btn, pressed) {
				var chart = Ext.getCmp('chartCmp');
				chart.animate = pressed ? {
					easing: 'ease',
					duration: 500
				} : false;
			}
		} ],
		items: chart
	});
});
