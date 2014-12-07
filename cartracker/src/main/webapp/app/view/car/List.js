/**
 * Grid for displaying Staff details
 */
Ext.define('CarTracker.view.car.List', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.car.list',
	requires: [ 'Ext.grid.column.Boolean', 'Ext.grid.column.Date', 'Ext.grid.column.Number' ],
	title: 'Manage Inventory',
	iconCls: 'icon_tag',
	store: 'Cars',
	viewConfig: {
		deferEmptyText: false,
		emptyText: 'Sorry, no cars matched your search criteria!',
		markDirty: false
	},
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			columns: {
				defaults: {},
				items: [ {
					text: 'Make',
					dataIndex: 'makeName',
					width: 100
				}, {
					text: 'Model',
					dataIndex: 'modelName',
					width: 100
				}, {
					text: 'Category',
					dataIndex: 'categoryName',
					width: 100
				}, {
					text: 'Year',
					dataIndex: 'year'
				}, {
					text: 'Color',
					dataIndex: 'colorName',
					width: 200
				}, {
					xtype: 'datecolumn',
					text: 'Acquired',
					dataIndex: 'acquisitionDate',
					dateFormat: 'Y-m-d',
					hidden: true
				}, {
					xtype: 'numbercolumn',
					text: 'List Price',
					dataIndex: 'listPrice'
				}, {
					xtype: 'numbercolumn',
					text: 'Sale Price',
					dataIndex: 'salePrice'
				}, {
					xtype: 'booleancolumn',
					text: 'Sold?',
					dataIndex: 'sold',
					trueText: 'Yes',
					falseText: 'No'
				}, {
					xtype: 'datecolumn',
					text: 'Sale Date',
					dataIndex: 'saleDate',
					dateFormat: 'Y-m-d',
					hidden: true
				}, {
					text: 'Status',
					dataIndex: 'statusName',
					width: 200
				} ]
			},
			dockedItems: [ {
				xtype: 'toolbar',
				dock: 'top',
				ui: 'footer',
				items: [ {
					xtype: 'button',
					itemId: 'add',
					iconCls: 'icon_add',
					text: 'Add to Inventory'
				}, '-', {
					xtype: 'button',
					itemId: 'search',
					iconCls: 'icon_search',
					text: 'Search Inventory'
				}, '-', {
					xtype: 'button',
					itemId: 'clear',
					iconCls: 'icon_delete',
					text: 'Clear Search'
				} ]
			}, {
				xtype: 'pagingtoolbar',
				ui: 'footer',
				defaultButtonUI: 'default',
				dock: 'bottom',
				displayInfo: true,
				store: me.getStore()
			} ]
		});
		me.callParent(arguments);
	}
});