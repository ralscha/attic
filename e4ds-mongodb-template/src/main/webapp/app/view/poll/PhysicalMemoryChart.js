Ext.define('E4ds.view.poll.PhysicalMemoryChart', {
	extend: 'Ext.chart.Chart',
	alias: 'widget.physicalmemorychart',

	animate: false,
	shadow: true,
	store: 'PollChart',
	legend: {
	    position: 'top'
	},
	
	labelRenderer: function(value) {
		return Math.round(value / (1024 * 1024)) + ' MB';
	},
	
	initComponent: function() {
		
		var me = this;
		
		me.axes = [ {
			type: 'Numeric',
			position: 'left',
			fields: [ 'freePhysicalMemorySize', 'totalPhysicalMemorySize' ],
			minimum: 0,
			title: 'Physical Memory',
			label: {
				renderer: this.labelRenderer
			},
			grid: true
		}, {
			type: 'Category',
			position: 'bottom',
			fields: [ 'time' ]
		} ];

		me.series = [ {
			type: 'line',
			fill: true,
			axis: 'left',
			xField: 'time',
			yField: 'freePhysicalMemorySize',
			title: 'Free Physical Memory Size'
		}, {
			type: 'line',
			fill: true,
			axis: 'left',
			xField: 'time',
			yField: 'totalPhysicalMemorySize',
			title: 'Total Physical Memory Size'
		} ];
		
		me.callParent(arguments);
	}

});