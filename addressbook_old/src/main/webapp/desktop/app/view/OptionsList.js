Ext.define('Addressbook.view.OptionsList', {
	controller: 'Addressbook.controller.Options',
	extend: 'Ext.grid.Panel',
	alias: 'widget.optionslist',
	requires: [ 'Ext.grid.plugin.RowEditing', 'Ext.toolbar.Paging', 'Addressbook.controller.Options', 'Ext.selection.RowModel' ],
	closable: true,
	border: true,

	extraColumns: null,
	initComponent: function() {
		var me = this, columns = [ {
			text: 'Name',
			dataIndex: 'name',
			editor: {
				xtype: 'textfield',
				allowBlank: false
			},
			flex: 1
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
				items: [ {
					text: 'Add',
					itemId: 'createButton',
					icon: 'resources/images/add.png'
				}, {
					text: 'Edit',
					itemId: 'editButton',
					disabled: true,
					icon: 'resources/images/edit.png'
				}, {
					text: 'Delete',
					itemId: 'destroyButton',
					disabled: true,
					icon: 'resources/images/delete.png'
				} ]
			} ],
			listeners: {
				'selectionchange': function(view, records) {
					me.down('#destroyButton').setDisabled(!records.length);
					me.down('#editButton').setDisabled(!records.length);
				}
			}
		});
		me.callParent(arguments);
	}
});