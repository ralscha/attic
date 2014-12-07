Ext.define('CarTracker.view.report.make.List', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.report.make.list',
	requires: [ 'Ext.grid.feature.Grouping', 'Ext.grid.feature.Summary' ],
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			store: {
				type: 'report.make'
			},
			features: [ {
				ftype: 'grouping',
				groupHeaderTpl: 'Make: {name}'
			}, {
				ftype: 'summary'
			} ],			
			columns: {
				items: [ {
					text: 'Model',
					dataIndex: 'model',
					summaryType: 'count',
					summaryRenderer: function(value, summaryData, dataIndex) {
						return '<strong>' + Ext.String.format('{0} Car{1}', value, value !== 1 ? 's' : '') + '</strong>';
					}
				}, {
					text: 'Total Sales',
					dataIndex: 'totalSales',
					renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
						return Ext.util.Format.usMoney(value);
					},
					summaryType: 'sum',
					summaryRenderer: function(value, summaryData, dataIndex) {
						return '<strong>Grand Total: ' + Ext.util.Format.usMoney(value) + '</strong>';
					},
					flex: 1
				} ]
			}
		});
		me.callParent(arguments);
	}
});