/**
 * Created by Administrator on 25/08/2015.
 */

Ext.define('Starter.view.inventory.Form', {
	extend: 'Ext.form.Panel',
	requires: [ 'Starter.Util', 'Ext.grid.plugin.RowEditing' ],

	glyph: 0xe803,
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	bodyPadding: 10,

	defaultFocus: 'textfield[name=userName]',

	items: [ {
		xtype: 'container',
		layout: 'column',
		items: [ {
			xtype: 'container',
			layout: 'form',
			columnWidth: 0.5,
			items: [ {
				xtype: 'textfield',
				fieldLabel: 'User Name',
				name: 'userName',
				allowBlank: false,
				emptyText: 'Create user name '
			}, {
				xtype: 'textfield',
				fieldLabel: 'Enroll No',
				name: 'enrollNo',
				allowBlank: false,
				emptyText: 'Create enroll no '
			}, {
				xtype: 'datefield',
				fieldLabel: 'Inventory Date',
				name: 'inventoryDate',
				format: 'd-m-Y',
				allowBlank: false
			} ]
		}, {
			xtype: 'container',
			layout: 'form',
			columnWidth: 0.5,
			items: [ {
				xtype: 'combobox',
				fieldLabel: 'Department',
				name: 'departmentId',
				allowBlank: false,
				emptyText: 'Select department...',
				displayField: 'departmentName',
				valueField: 'id',
				queryMode: 'local',
				bind: {
					store: '{departments}'
				}
			}, {
				xtype: 'combobox',
				fieldLabel: 'Section',
				name: 'sectionId',
				allowBlank: false,
				emptyText: 'Select section...',
				displayField: 'sectionName',
				valueField: 'id',
				queryMode: 'local',
				bind: {
					store: '{sections}'
				}
			}, {
				xtype: 'combobox',
				fieldLabel: 'Location',
				name: 'locationId',
				allowBlank: false,
				emptyText: 'Select location...',
				displayField: 'locationName',
				valueField: 'id',
				queryMode: 'local',
				bind: {
					store: '{locations}'
				}
			} ]
		} ]
	}, {
		padding: '0 10 5 10',
		xtype: 'textareafield',
		fieldLabel: 'Remark',
		name: 'remark'
	}, {
		xtype: 'grid',
		flex: 1,
		reference: 'detailGrid',
		pageSize: 10,
		margin: '10 0 0 0',
		// title: 'Items',
		// glyph: 0xe803,

		plugins: {
			// xclass: 'Ext.grid.plugin.RowEditing',
			// clicksToEdit: 2
			xclass: 'Ext.grid.plugin.CellEditing'
		},

		bind: {
			store: '{selectedInventoryHeader.inventoryDetails}'
		},
		tbar: [ {
			text: 'Add Item',
			glyph: 0xe807,
			handler: 'onAddItemClick'
		} ],
		columns: [ {
			text: 'CODE',
			dataIndex: 'itemId',
			flex: 1,
			renderer: 'itemCodeRenderer',
			editor: {
				xtype: 'combobox',
				allowBlank: false,
				emptyText: 'Select item...',
				displayField: 'code',
				valueField: 'id',
				queryMode: 'local',
				bind: {
					store: '{items}'
				}
			}
		}, {
			text: 'NAME',
			dataIndex: 'itemId',
			flex: 1,
			renderer: 'itemNameRenderer',
			editor: null
		}, {
			text: 'QTY',
			dataIndex: 'quantity',
			width: 100,
			editor: {
				xtype: 'numberfield',
				allowBlank: false
			}
		}, {
			text: 'UOM',
			dataIndex: 'itemId',
			flex: 1,
			renderer: 'itemUomRenderer',
			editor: null
		}, {			
			xtype: 'checkcolumn',
			text: 'Use',
			dataIndex: 'useItem',			
			width: 100
		}, {
			xtype: 'widgetcolumn',
			width: 150,
			widget: {
				xtype: 'button',
				text: 'Remove',
				handler: 'onRemoveItemClick'
			}
		} ]
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.setup_inventory,
			handler: 'back',
			glyph: 0xe818
		}, '->', {
			text: i18n.destroy,
			glyph: 0xe806,
			handler: 'destroyInventory',
			bind: {
				hidden: '{newInventory}'
			}
		}, {
			text: Starter.Util.underline(i18n.save, 'S'),
			accessKey: 's',
			glyph: 0xe80d,
			formBind: true,
			handler: 'save'
		} ]
	} ]

});
