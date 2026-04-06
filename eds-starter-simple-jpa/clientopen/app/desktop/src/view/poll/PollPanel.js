Ext.define('Starter.view.poll.PollPanel', {
    extend: 'Ext.Panel',

    requires: ['Ext.chart.axis.Category', 'Ext.chart.axis.Numeric', 'Ext.chart.series.Bar', 'Ext.chart.CartesianChart'],

    controller: {
        xclass: 'Starter.view.poll.PollController'
    },
    viewModel: {
        xclass: 'Starter.view.poll.PollModel'
    },

    title: 'POLL',
    layout: 'fit',

    items: [{
        xtype: 'chart',
        animate: true,
        insetPadding: 5,
        margin: '10 0 0 0',

        bind: {
            store: '{pagehits}'
        },

        axes: [{
            type: 'category',
            fields: ['month'],
            position: 'bottom'
        }, {
            type: 'numeric',
            fields: ['hit'],
            position: 'left'
        }],
        series: [{
            type: 'bar',
            label: {
                display: 'insideEnd',
                field: 'hit',
                color: '#333'
            },
            xField: 'month',
            yField: 'hit'
        }]
    }, {
        xtype: 'toolbar',
        docked: 'top',
        items: [{
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
        }]
    }]

});
