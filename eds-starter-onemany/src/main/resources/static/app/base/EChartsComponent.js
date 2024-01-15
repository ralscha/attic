Ext.define('Starter.base.EChartsComponent', {
	extend: 'Ext.Component',

	constructor: function(config) {
		if (config.requireechart) {
			require(config.requireechart);
		}

		this.callParent(arguments);
	},

	afterRender: function() {
		this.on('resize', this._onResize);
	},

	_onResize: function() {
		if (this.chart) {
			this.chart.resize();
		}
	},

	destroy: function() {
		this.un('resize', this._onResize);
		if (this.chart) {
			this.chart.dispose();
			delete this.chart;
		}
		this.callParent(arguments);
	},

	drawChart: function(chartOption, notMerge) {
		if (!this.chart) {
			this.chart = require('echarts').init(this.el.dom);
		}

		this.chart.setOption(chartOption, notMerge);
	}

});