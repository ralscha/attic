Ext.define('Sales.view.bill.Edit', {
	extend: 'Sales.form.Panel',
	alias: 'widget.myapp-bill-edit',
	itemId: 'screen-bill-edit',
	api: {
		load: 'MyAppBill.readForm',
		submit: 'MyAppBill.writeForm'
	},
	paramOrder: [ 'id' ],
	initComponent: function() {
		var me = this;
		// Stores
		Ext.applyIf(me, {
			customerStore: Ext.create('Ext.data.Store', {
				fields: [ 'id', 'name' ],
				data: [ {
					"id": 0,
					"name": "Sencha"
				}, {
					"id": 1,
					"name": "Xenophy"
				} ]
			}),
			itemStore: Ext.create('Ext.data.Store', {
				storeId: 'billItemStore',
				fields: [ 'desc', 'qty', 'price', 'sum' ],
				data: {
					items: [ {
						'desc': 'Sencha Complete',
						"qty": "5",
						"price": "995",
						"sum": 4975
					}, {
						'desc': 'Sencha Ext JS + Standard Support',
						"qty": "5",
						"price": "595",
						"sum": 2975
					} ]
				},
				proxy: {
					type: 'memory',
					reader: {
						type: 'json',
						root: 'items'
					}
				}
			})
		});
		// Fields & Grid
		Ext.apply(me, {
			bodyPadding: 20,
			items: [ {
				padding: '0 0 20 0',
				width: 500,
				xtype: 'combo',
				fieldLabel: 'customer',
				name: 'customer',
				// store: me.customerStore,
				store: 'Customer',
				editable: false,
				displayField: 'name',
				valueField: 'id'
			}, {
				// Grid Panel
				height: 400,
				padding: '0 0 20 0',
				xtype: 'grid',
				// store: me.itemStore,
				store: 'BillItem',
				plugins: [ Ext.create('Ext.grid.plugin.CellEditing') ],
				columns: [ {
					text: 'Description',
					dataIndex: 'desc',
					flex: 1,
					editor: true
				}, {
					text: 'Qty',
					dataIndex: 'qty',
					editor: {
						xtype: 'numberfield',
						allowBlank: false,
						minValue: 0,
						maxValue: 10000
					}
				}, {
					text: 'Price',
					dataIndex: 'price',
					renderer: Ext.util.Format.usMoney,
					editor: {
						xtype: 'numberfield',
						allowBlank: false,
						minValue: 0,
						maxValue: 10000
					}
				}, {
					text: 'Sum',
					dataIndex: 'sum',
					renderer: Ext.util.Format.usMoney
				} ],
				tbar: [ {
					text: 'Add Item',
					action: 'add-item'
				}, '-', {
					text: 'Remove Item',
					action: 'remove-item'
				} ]
			}, {
				name: 'items',
				xtype: 'hidden',
				value: '[]'
			}, {
				name: 'parentId',
				xtype: 'hidden',
				value: ''
			}, {
				fieldLabel: 'note',
				// update cdoe
				name: 'note',
				xtype: 'textarea',
				width: 500
			} ]
		});
		// TopToolbar
		Ext.apply(me, {
			tbar: [ {
				text: 'Save',
				action: 'save'
			} ]
		});
		me.callParent(arguments);
	}
});
