Ext.define('BitP.view.workflow.List', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Ext.grid.column.Date', 'Ext.grid.plugin.RowExpander' ],

	viewConfig: {
		deferEmptyText: false,
		emptyText: 'No workflow history was found for the selected record',
		markDirty: false
	},
	plugins: [ {
		ptype: 'rowexpander',
		rowBodyTpl: new Ext.XTemplate('<b>Notes:</b> {notes}')
	} ],
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			columns: {
				defaults: {
					sortable: false
				},
				items: [ {
					text: 'From',
					dataIndex: 'lastStatus',
					width: 100
				}, {
					text: 'To',
					dataIndex: 'nextStatus',
					width: 100
				}, {
					text: 'Submitted By',
					dataIndex: 'lastName',
					renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
						return value + ', ' + record.get('firstName');
					},
					width: 200
				}, {
					xtype: 'datecolumn',
					text: 'Submitted',
					dataIndex: 'createDate',
					format: 'Y-m-d H:i:s',
					flex: 1
				} ]
			}
		});
		me.callParent(arguments);
	}
});