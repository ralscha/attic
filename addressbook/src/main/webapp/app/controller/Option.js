Ext.define('Ab.controller.Option', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			edit: 'onEdit',
			canceledit: 'onCancelEdit',
			removed: 'onRemoved'
		},
		gridview: {
			selector: 'gridview',
			listeners: {
				itemadd: 'onItemAdd'
			}
		},
		actionColumn: {
			selector: 'actioncolumn',
			listeners: {
				click: 'onActionColumnClick'
			}
		},		
		createButton: {
			click: 'onCreateButtonClick'
		}
	},

	init: function() {
		var store = this.getView().getStore();
		store.clearFilter(true);
		store.load();
	},
	
	destroy: function() {
		if (this.actionMenu) {
			this.actionMenu.destroy();
		}
		return this.callParent(arguments);
	},	
		
	onItemContextMenu: function(view, record, item, index, e, eOpts) {
		e.stopEvent();
		this.showContextMenu(record, e.getXY());
	},

	onActionColumnClick: function(grid, rowIndex, colIndex, item, e, record, row) {
		this.showContextMenu(record, null, row);
	},

	showContextMenu: function(record, xy, item) {
		var me = this;
		var items = me.buildContextMenuItems(record);

		if (this.actionMenu) {
			this.actionMenu.destroy();
		}

		this.actionMenu = Ext.create('Ext.menu.Menu', {
			items: items,
			border: true
		});

		if (xy) {
			this.actionMenu.showAt(xy);
		} else {
			this.actionMenu.showBy(item);
		}
	},
	
	buildContextMenuItems: function(record) {

		return [ {
			text: i18n.edit,
			glyph: 0xe803,
			handler: Ext.bind(this.editObject, this, [ record ])
		}, {
			text: i18n.destroy,
			glyph: 0xe806,
			handler: Ext.bind(this.destroyObject, this, [ record ])
		} ];

	},
	
	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
	},

	onCreateButtonClick: function() {
		var me = this, grid = me.getView(), plugin = grid.editingPlugin, store = grid.getStore();
		// if we're already editing, don't allow new record insert
		if (plugin.editing) {
			// show error message
			Ext.Msg.alert('Attention', 'Please finish editing before inserting a new record');
			return false;
		}

		plugin.editor.cancelEdit();
		store.insert(0, {});
	},

	
	editObject: function(record) {
		this.onItemAdd([record]);
	},

	onItemAdd: function(records) {
		var me = this, grid = me.getView(), plugin = grid.editingPlugin;
		plugin.startEdit(records[0], 0);
	},

	destroyObject: function(record) {
		var me = this;
		var store = me.getView().getStore();

		Ext.Msg.confirm(i18n.attention, this.destroyConfirmMsg(record), function(buttonId, text, opt) {
			if (buttonId === 'yes') {
				var record = me.getView().getSelectionModel().getSelection()[0];
				store.remove(record);
				store.sync({
					success: function() {
						Ab.ux.window.Notification.info(i18n.successful, me.successfulDestroyMsg);
					},
					failure: function(records, operation) {
						store.rejectChanges();
					}
				});
			}
		});
	},
	
	destroyConfirmMsg: function(record) {
		return record.get('name') + ' ' + i18n.reallyDestroy;
	},

	onCancelEdit: function(editor, context, eOpts) {
		if (context.record.phantom) {
			context.store.remove(context.record);
		}
	},

	onEdit: function(editor, context, eOpts) {
		var me = this, store = context.record.store;
		store.sync({
			success: function() {
				Ab.ux.window.Notification.info(i18n.successful, me.successfulSaveMsg);
			},
			failure: function() {
				var grid = me.getView(), plugin = grid.editingPlugin;
				plugin.startEdit(context.record, 0);
			}
		});
	}

});