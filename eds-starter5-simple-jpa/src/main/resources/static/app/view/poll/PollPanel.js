Ext.define('SimpleApp.view.poll.PollPanel', {
	extend: 'Ext.panel.Panel',

	requires: [ 'SimpleApp.view.poll.PollController', 'SimpleApp.view.poll.PollModel' ],

	controller: {
		xclass: 'SimpleApp.view.poll.PollController'
	},
	viewModel: {
		xclass: 'SimpleApp.view.poll.PollModel'
	},

	title: 'POLL',
	layout: 'fit',

	items: [ {
		xtype: 'chart',
		animate: true,
		insetPadding: 5,
		margin: '10 0 0 0',

		bind: {
			store: '{pagehits}'
		},

		axes: [ {
			type: 'category',
			fields: [ 'month' ],
			position: 'bottom'
		}, {
			type: 'numeric',
			fields: [ 'hit' ],
			position: 'left'
		} ],
		series: [ {
			type: 'bar',
			label: {
				display: 'insideEnd',
				field: 'hit',
				color: '#333'
			},
			xField: 'month',
			yField: 'hit'
		} ]
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: 'Start Polling',
			handler: 'startPolling',
			bind: {
				disabled: '{running}'
			}
		}, {
			text: 'Stop Polling',
			handler: 'stopPolling',
			bind: {
				disabled: '{!running}'
			}
		} ]
	} ]

});