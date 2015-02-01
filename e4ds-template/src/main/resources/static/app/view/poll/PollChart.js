Ext.define('E4ds.view.poll.PollChart', {
	extend: 'Ext.panel.Panel',
	controller: 'E4ds.controller.PollChart',
	title: i18n.chart_title,
	border: true,
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	closable: true,

	requires: [ 'Ext.chart.*', 'E4ds.view.poll.HeapMemoryChart', 'E4ds.view.poll.PhysicalMemoryChart', 'E4ds.store.PollChart' ],

	initComponent: function() {

		var me = this;
		me.store = Ext.create('E4ds.store.PollChart');

		me.dockedItems = [ {
			xtype: 'toolbar',
			items: [ {
				itemId: 'startStopButton',
				text: i18n.chart_stop,
				glyph: 0xe805,
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
			items: [ Ext.create('E4ds.view.poll.HeapMemoryChart', {
				store: me.store,
				flex: 1
			}), Ext.create('E4ds.view.poll.PhysicalMemoryChart', {
				store: me.store,
				flex: 1
			}) ]
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
				store: me.store,
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
					colorSet: [ '#82B525', '#ddd' ]
				} ]
			}, {
				xtype: 'chart',
				animate: true,
				store: me.store,
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
					colorSet: [ '#3AA8CB', '#ddd' ]
				} ]

			} ]
		} ];

		me.callParent(arguments);
	}
});
