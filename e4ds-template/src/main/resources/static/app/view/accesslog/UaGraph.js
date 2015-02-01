Ext.define('E4ds.view.accesslog.UaGraph', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.uagraph',
	itemId: 'uaGraphPanel',

	requires: [ 'Ext.chart.axis.Category', 'Ext.chart.axis.Numeric',
			'Ext.chart.series.Bar', 'E4ds.store.AccessLogUserAgents',
			'E4ds.store.AccessLogYears' ],

	layout: {
		type: 'fit'
	},

	title: i18n.accesslog_userAgents,

	initComponent: function() {
		var me = this;

		var fields = [ 'IE', 'Chrome', 'Firefox', 'Safari', 'Opera', 'Other' ];

		me.items = [ {
			xtype: 'chart',
			itemId: 'uaChart',
			animate: true,
			defaultInsets: 30,
			store: Ext.create('E4ds.store.AccessLogUserAgents'),
			legend: {
				position: 'right'
			},
			axes: [ {
				type: 'Numeric',
				position: 'left',
				fields: fields,
				title: i18n.accesslog_userAgent_usage,
				grid: true,
				decimals: 0,
				minimum: 0,
				maximum: 100
			}, {
				type: 'Category',
				position: 'bottom',
				fields: [ 'yearMonth' ],
				title: i18n.accesslog_userAgent_month
			} ],
			series: [ {
				type: 'area',
				axis: 'left',
				highlight: true,
				tips: {
					trackMouse: true,
					renderer: function(storeItem, item) {
						var d = storeItem.get('yearMonth'), percent = storeItem
								.get(item.storeField)
								+ '%';
						this.setTitle(item.storeField + ' - ' + d + ' - ' + percent);
					}
				},
				xField: 'name',
				yField: fields,
				style: {
					lineWidth: 1,
					stroke: '#666',
					opacity: 0.86
				}
			} ]
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				xtype: 'combobox',
				fieldLabel: i18n.accesslog_userAgent_year,
				allowBlank: false,
				labelWidth: 40,
				itemId: 'uaYearsCB',
				store: Ext.create('E4ds.store.AccessLogYears'),
				valueField: 'year',
				displayField: 'year',
				queryMode: 'local',
				forceSelection: true
			} ]
		} ];

		me.callParent(arguments);
	}

});