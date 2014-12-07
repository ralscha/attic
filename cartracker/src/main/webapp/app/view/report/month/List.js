Ext.define('CarTracker.view.report.month.List', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.report.month.list',
	requires: [ 'Ext.grid.feature.Summary' ],
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			store: Ext.create('CarTracker.store.report.Months'),
			features: [ {
				ftype: 'summary'
			} ],
			columns: {
				items: [ {
					text: 'Month',
					dataIndex: 'month',
					sortable: false
				}, {
					text: 'No. Sold',
					dataIndex: 'totalSold',
					summaryType: 'sum',
					summaryRenderer: function(value, summaryData, dataIndex) {
						return '<strong>Total Sold: ' + value + '</strong>';
					},
					flex: 1,
					sortable: false
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
					flex: 1,
					sortable: false
				} ]
			}
		});
		me.callParent(arguments);
	}
});