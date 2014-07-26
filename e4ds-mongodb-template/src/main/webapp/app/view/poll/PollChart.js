Ext.define('E4ds.view.poll.PollChart', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.pollchart',
	stateId: 'pollChart',
	title: i18n.chart_title,

	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	closable: true,

	requires: [ 'Ext.chart.*', 'E4ds.view.poll.HeapMemoryChart', 'E4ds.view.poll.PhysicalMemoryChart' ],

	initComponent: function() {

		var me = this;

		me.dockedItems = [ {
			xtype: 'toolbar',
			items: [ {
				text: i18n.chart_stop,
				iconCls: 'icon-stop',
				action: 'control'
			} ]
		} ];

		me.items = [ {
			xtype: 'container',
			flex: 1,
			layout: {
				type: 'hbox',
				align: 'stretch'
			},
			items: [ {
				xtype: 'heapmemorychart',
				flex: 1
			}, {
				xtype: 'physicalmemorychart',
				flex: 1
			} ]
		}, {
			xtype: 'container',
			flex: 1,
			layout: {
				type: 'hbox',
				align: 'stretch'
			},
			items: [ {
			xtype: 'chart',
			animate: true,
				store: 'PollChart',
				insetPadding: 40,
				flex: 1,				
			axes: [ {
					type: 'gauge',
					position: 'gauge',
				minimum: 0,
					maximum: 100,
					steps: 10,
					margin: 5,
					title: 'System CPU Load'
				} ],
				series: [ {
					type: 'gauge',
					field: 'systemCpuLoad',
					donut: 30,
	                colorSet: ['#82B525', '#ddd']
				} ]
			}, {

				xtype: 'chart',
				animate: true,
				store: 'PollChart',
				insetPadding: 40,
				flex: 1,				
				axes: [ {
					type: 'gauge',
					position: 'gauge',
					minimum: 0,
					maximum: 100,
					steps: 10,
					margin: 5,
					title: 'Process CPU Load'
			} ],
			series: [ {
					type: 'gauge',
					field: 'processCpuLoad',
					donut: 30,
	                colorSet: ['#3AA8CB', '#ddd']
				} ]
			
			} ]
		} ];

		me.callParent(arguments);
	}
});
