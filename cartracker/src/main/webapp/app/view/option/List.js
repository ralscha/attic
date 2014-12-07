Ext.define('CarTracker.view.option.List', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.option.list',
	requires: [ 'Ext.grid.plugin.RowEditing', 'Ext.toolbar.Paging', 'Ext.grid.column.Boolean' ],
	extraColumns: null,
	initComponent: function() {
		var me = this, columns = [ {
			text: 'Abbreviation',
			dataIndex: 'shortName',
			editor: {
				xtype: 'textfield'
			},
			flex: .2
		}, {
			text: 'Name',
			dataIndex: 'longName',
			editor: {
				xtype: 'textfield',
				allowBlank: false
			},
			flex: .5
		}, {
			xtype: 'booleancolumn',
			text: 'Active',
			dataIndex: 'active',
			trueText: 'Yes',
			falseText: 'No'
		} ];
		if (!Ext.isEmpty(me.extraColumns)) {
			columns.unshift(me.extraColumns);
		}
		Ext.applyIf(me, {
			selType: 'rowmodel',
			plugins: [ {
				ptype: 'rowediting',
				clicksToEdit: 2
			} ],
			columns: {
				defaults: {},
				items: columns
			},
			dockedItems: [ {
				xtype: 'toolbar',
				dock: 'top',
				ui: 'footer',
				items: [ {
					xtype: 'button',
					itemId: 'add',
					iconCls: 'icon_add',
					text: 'Add Item'
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