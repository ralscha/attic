Ext.define('Sales.view.bill.List', {
	extend: 'Sales.grid.Panel',
	alias: 'widget.myapp-bill-list',
	itemId: 'screen-bill-list',
	requires: [ 'Sales.form.SearchField', 'Ext.selection.CheckboxModel' ],
	initComponent: function() {
		var me = this, store = me.getStore();
		if (!store) {
			store = Ext.create('Sales.store.Bill');
			me.store = store;
		}
		Ext.apply(me, {
			selModel: Ext.create('Ext.selection.CheckboxModel')
		});
		Ext.apply(me, {
			tbar: [ {
				text: 'Add',
				disabled: true,
				action: 'add'
			}, {
				text: 'Edit',
				disabled: true,
				action: 'edit'
			}, {
				text: 'Remove',
				disabled: true,
				action: 'remove'
			}, '-', {
				text: 'Refresh',
				disabled: true,
				action: 'refresh'
			}, '->', {
				xtype: 'myapp-searchfield',
				disabled: true,
				width: 150
			} ],
			bbar: {
				xtype: 'pagingtoolbar',
				store: store,
				displayInfo: true
			}
		});
		Ext.apply(me, {
			columns: [ {
				text: 'Customer',
				dataIndex: 'customer',
				flex: 1
			}, {
				text: 'Address',
				dataIndex: 'addr',
				flex: 1
			}, {
				text: 'Note',
				dataIndex: 'note',
				flex: 1
			}, {
				text: 'Modified',
				dataIndex: 'modified',
				width: 120
			}, {
				text: 'Created',
				dataIndex: 'created',
				width: 120
			} ]
		});
		me.callParent(arguments);
	}
});
