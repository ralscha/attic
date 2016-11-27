Ext.define('Starter.view.accesslog.OsPie', {
	extend: 'Starter.base.EChartsComponent',

	updateData: function(value) {
		this.chartOption.series[0].data = [];
		for (var i = 0; i < value.length; i++) {
			this.chartOption.series[0].data.push({
				value: value[i][1],
				name: value[i][0]
			});
		}

		this.drawChart(this.chartOption, true);
	},

	chartOption: {
		tooltip: {
			trigger: 'item',
			formatter: '{b} : {c} ({d}%)'
		},
		series: [ {
			name: 'OS',
			type: 'pie',
			selectedOffset: 100,
			center: [ '30%', '50%' ],
			itemStyle: {
				normal: {
					label: {
						position: 'inner'
					},
					labelLine: {
						show: false
					}
				},
				emphasis: {
					label: {
						show: true,
						position: 'inner'
					},
					labelLine: {
						show: false
					}
				}
			}
		} ]

	}

});