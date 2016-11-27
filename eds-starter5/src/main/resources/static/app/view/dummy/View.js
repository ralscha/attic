Ext.define('Starter.view.dummy.View', {
	extend: 'Ext.panel.Panel',
	requires: [ 'Starter.view.dummy.Controller', 'Starter.view.dummy.ViewModel' ],
	title: 'Dashboard',
	closable: true,

	controller: {
		xclass: 'Starter.view.dummy.Controller'
	},

	viewModel: {
		xclass: 'Starter.view.dummy.ViewModel'
	},

	layout: {
		type: 'vbox',
		align: 'stretch'
	},

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.dummy_callSecuredService,
			handler: 'callSecuredService'
		} ]
	} ],

	items: [ {
		xtype: 'container',
		height: 250,
		layout: {
			type: 'hbox',
			align: 'stretch'
		},
		items: [ {
			xclass: 'Starter.base.EChartsComponent',
			reference: 'barchart',
			requireechart: ['echarts', 'echarts/chart/bar'],
			flex: 3
		}, {
			xclass: 'Starter.base.EChartsComponent',
			reference: 'radarchart',
			requireechart: ['echarts', 'echarts/chart/radar'],
			flex: 1
		} ]
	}, {
		xtype: 'container',
		layout: {
			type: 'hbox',
			align: 'stretch'
		},
		flex: 3,
		items: [ {
			xtype: 'grid',
			flex: 6,
			bind: {
				store: '{companies}',
				selection: '{companySelection}'
			},
			defaults: {
				sortable: true
			},
			columns: [ {
				text: i18n.dummy_company,
				flex: 1,
				dataIndex: 'name'
			}, {
				text: i18n.dummy_price,
				width: null,
				dataIndex: 'price',
				formatter: 'usMoney'
			}, {
				text: i18n.dummy_revenue,
				width: null,
				dataIndex: 'revenue',
				renderer: 'percentRenderer'
			}, {
				text: i18n.dummy_growth,
				width: null,
				dataIndex: 'growth',
				renderer: 'percentRenderer',
				hidden: true
			}, {
				text: i18n.dummy_product,
				width: null,
				dataIndex: 'product',
				renderer: 'percentRenderer',
				hidden: true
			}, {
				text: i18n.dummy_market,
				width: null,
				dataIndex: 'market',
				renderer: 'percentRenderer',
				hidden: true
			} ],
			listeners: {
				select: 'onGridSelect'
			}
		}, {
			xtype: 'form',
			flex: 3,
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			bind: {
				disabled: '{!companySelection}'
			},
			
			margin: '0 0 0 5',
			items: [ {
				margin: '2',
				xtype: 'fieldset',
				flex: 1,
				title: i18n.dummy_nocompany,
				bind: {
					title: '{companySelection.name}'
				},
				defaults: {
					maxValue: 100,
					minValue: 0,
					labelWidth: 90,
					enforceMaxLength: true,
					maxLength: 5,
					bubbleEvents: [ 'change' ]
				},
				defaultType: 'numberfield',
				items: [ {
					fieldLabel: i18n.dummy_price,
					bind: '{companySelection.price}'
				}, {
					fieldLabel: i18n.dummy_revenue + ' %',
					bind: '{companySelection.revenue}'
				}, {
					fieldLabel: i18n.dummy_growth + ' %',
					bind: '{companySelection.growth}'
				}, {
					fieldLabel: i18n.dummy_product + ' %',
					bind: '{companySelection.product}'
				}, {
					fieldLabel: i18n.dummy_market + ' %',
					bind: '{companySelection.market}'
				} ],
				listeners: {
					buffer: 200,
					change: 'formChange'
				}
			} ]
		} ]
	} ]

});