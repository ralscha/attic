Ext.define('E4ds.view.accesslog.OsGraph', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.osgraph',
	requires: [ 'E4ds.store.AccessLogOs', 'E4ds.store.AccessLogYears' ],
	itemId: 'osGraphPanel',

	layout: {
		type: 'fit'
	},

	title: i18n.accesslog_operatingSystems,

	initComponent: function() {
		var me = this;
		var store = Ext.create('E4ds.store.AccessLogOs');

		me.items = [ {
			xtype: 'chart',
			itemId: 'osChart',
			animate: true,
			store: store,
			shadow: true,
			legend: {
				position: 'right'
			},
			series: [ {
				type: 'pie',
				field: 'value',
				showInLegend: true,
				tips: {
					trackMouse: true,
					renderer: function(storeItem, item) {
						var total = 0;
						store.each(function(rec) {
							total += rec.get('value');
						});
						this.setTitle(storeItem.get('name') + ': ' + Math.round(storeItem.get('value') / total * 100) + '%');
					}
				},
				highlight: {
					segment: {
						margin: 20
					}
				},
				label: {
					field: 'name',
					display: 'rotate',
					contrast: true,
					font: '16px Arial'
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
				itemId: 'osYearsCB',
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