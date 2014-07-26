Ext.define('E4ds.view.poll.HeapMemoryChart', {
	extend: 'Ext.chart.Chart',
	alias: 'widget.heapmemorychart',

	animate: true,
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
			fields: [ 'maxHeapMemory', 'committedHeapMemory', 'usedHeapMemory' ],
			minimum: 0,
			title: 'Heap Memory',
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
			axis: 'left',
			xField: 'time',
			yField: 'maxHeapMemory',
			title: 'Max'
		}, {
			type: 'line',
			axis: 'left',
			xField: 'time',
			yField: 'committedHeapMemory',
			title: 'Committed'
		}, {
			type: 'line',
			axis: 'left',
			xField: 'time',
			yField: 'usedHeapMemory',
			title: 'Used'
		} ];
		
		me.callParent(arguments);
	}

});