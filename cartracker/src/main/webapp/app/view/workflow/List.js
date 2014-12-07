/**
 * Grid for displaying Workflow details for the selected Car
 */
Ext.define('CarTracker.view.workflow.List', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.workflow.list',
	requires: [ 'Ext.grid.column.Date', 'Ext.grid.plugin.RowExpander' ],
	store: 'Workflows',
	carId: null,
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
					dataIndex: 'lastStatusName',
					width: 100
				}, {
					text: 'To',
					dataIndex: 'nextStatusName',
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