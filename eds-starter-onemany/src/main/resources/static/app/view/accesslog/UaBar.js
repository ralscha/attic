Ext.define('Starter.view.accesslog.UaBar', {
	extend: 'Starter.base.EChartsComponent',

	updateData: function(value) {
		var i;
		this.chartOption.series = [];

		this.chartOption.xAxis[0].data = [];
		for (i = 0; i < 12; i++) {
			this.chartOption.xAxis[0].data.push(Ext.Date.getShortMonthName(i));
		}

		for (i = 0; i < value.length; i++) {

			this.chartOption.series.push({
				name: this.chartOption.legend.data[i],
				type: 'bar',
				stack: 'stack',
				itemStyle: {
					normal: {
						label: {
							show: true,
							position: 'insideRight'
						}
					}
				},
				data: value[i]
			});
		}

		this.drawChart(this.chartOption, true);
	},

	chartOption: {
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'shadow'
			}
		},
		legend: {
			data: [ 'Chrome', 'Firefox', 'IE', 'Safari', 'Other' ]
		},
		yAxis: [ {
			type: 'value'
		} ],
		xAxis: [ {
			type: 'category',
			data: [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ]
		} ]
	}

});