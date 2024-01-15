Ext.define('Ab.view.option.List', {
	controller: 'Ab.controller.Option',
	extend: 'Ext.grid.Panel',
	alias: 'widget.optionlist',
	requires: [ 'Ext.grid.plugin.RowEditing', 'Ext.toolbar.Paging', 'Ab.controller.Option', 'Ext.selection.RowModel' ],
	closable: true,
	border: true,

	extraColumns: null,
	initComponent: function() {
		var me = this, columns = [ {
			xtype: 'actioncolumn',
			width: 30,
			items: [ {
				icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAAK0lEQVR4AWMgBBhXyRFQsPI/xQoyCCgg7EgX2jkSYQWZAOFN2jtSjsKQBAD0NQ+N4ZAsdgAAAABJRU5ErkJggg=='
			} ]
		}, {
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
					text: i18n.create,
					itemId: 'createButton',
					glyph: 0xe807
				} ]
			} ]
		});
		
		me.callParent(arguments);
	}
});