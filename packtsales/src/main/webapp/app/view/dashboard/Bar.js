Ext.define('Sales.view.dashboard.Bar', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.myapp-dashboard-bar',
	title: 'Bar Chart',
	layout: 'fit',
	requires: [ 'Sales.store.Bar' ],
	initComponent: function() {
		var me = this, store;
		store = Ext.create('Sales.store.Bar');
		Ext.apply(me, {
			items: [ {
				xtype: 'chart',
				store: store,
				axes: [ {
					type: 'Numeric',
					position: 'bottom',
					fields: [ 'quotation', 'bill' ],
					title: 'Document Count',
					grid: true,
					minimum: 0
				}, {
					type: 'Category',
					position: 'left',
					fields: [ 'name' ],
					title: 'Customers'
				} ],
				series: [ {
					type: 'bar',
					axis: 'bottom',
					highlight: true,
					tips: {
						trackMouse: true,
						width: 140,
						height: 28,
						renderer: function(storeItem, item) {
							var tail = '';
							if (storeItem.get(item.yField) > 1) {
								tail = 's';
							}
							this.setTitle([ storeItem.get('name'), ': ', storeItem.get(item.yField), ' ', item.yField, ].join(''));
						}
					},
					label: {
						display: 'insideEnd',
						field: 'quotation',
						renderer: Ext.util.Format.numberRenderer('0'),
						orientation: 'horizontal',
						color: '#333',
						'text-anchor': 'middle'
					},
					xField: 'name',
					yField: [ 'quotation', 'bill' ]
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});
