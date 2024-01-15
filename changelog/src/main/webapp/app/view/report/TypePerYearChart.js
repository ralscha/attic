Ext.define('Changelog.view.report.TypePerYearChart', {
	extend: 'Ext.panel.Panel',
	controller: 'Changelog.controller.TypePerYearChartController',

	layout: {
		type: 'border'
	},
	
	bodyStyle: {
	    background: 'white'
	},
	
	title: i18n.report_typePerYear,
	closable: true,

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'container',
			region: 'north',
			layout: {
				padding: 10,
				type: 'hbox'
			},
			items: [ {
				xtype: 'combobox',
				itemId: 'yearCB',
				fieldLabel: i18n.year,
				store: Ext.create('Changelog.store.ChangeYears'),
				queryMode: 'local',
				displayField: 'year',
				valueField: 'year',
				forceSelection: true
			} ]
		}, {
			xtype: 'chart',
			itemId: 'chart',
			region: 'center',
			animate: true,
			shadow: true,
			store: Ext.create('Changelog.store.TypePerYearChart'),
			legend: {
				position: 'right'
			},
			axes: [ {
				type: 'Numeric',
				position: 'bottom',
				fields: [ 'fix', 'enhancement', 'new' ],
				title: false,
				grid: true
			}, {
				type: 'Category',
				position: 'left',
				fields: [ 'month' ],
				title: false,
				label: {
					renderer: function(v) {
						return Ext.Date.getShortMonthName(v - 1);
					}
				}
			} ],
			series: [ {
				type: 'bar',
				axis: 'bottom',
				gutter: 80,
				xField: 'month',
				yField: [ 'fix', 'enhancement', 'new' ],
				stacked: true,
				tips: {
					trackMouse: true,
					renderer: function(storeItem, item) {
						this.setTitle(String(item.value[1]));
					}
				}
			} ]
		} ];

		me.callParent(arguments);
	}

});