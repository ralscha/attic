Ext.define('Addressbook.controller.Options', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			edit: 'onEdit',
			canceledit: 'onCancelEdit',
			removed: 'onRemoved'
		},
		gridview: {
			selector: "gridview",
			listeners: {
				itemadd: 'onItemAdd'
			}
		},
		createButton: {
			click: 'onCreateButtonClick'
		},
		editButton: {
			click: 'onEditButtonClick'
		},
		destroyButton: {
			click: 'onDestroyButtonClick'
		}
	},

	init: function() {
		var store = this.getView().getStore();
		store.clearFilter(true);
		store.load();
	},
	
	onRemoved: function() {
		History.pushState({}, "Addressbook", "?");
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

	onEditButtonClick: function() {
		this.onItemAdd(this.getView().getSelectionModel().getSelection());
	},

	onItemAdd: function(records) {
		var me = this, grid = me.getView(), plugin = grid.editingPlugin;
		plugin.startEdit(records[0], 0);
	},

	onDestroyButtonClick: function() {
		var me = this;
		var store = me.getView().getStore();

		Ext.Msg.confirm('Attention', 'Are you sure you want to delete this item? This action cannot be undone.', function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				var record = me.getView().getSelectionModel().getSelection()[0];
				store.remove(record);
				store.sync({
					success: function() {
						Addressbook.ux.window.Notification.info("Successful", "Item deleted");
					},
					failure: function(records, operation) {
						store.rejectChanges();
					}
				});
			}
		});
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
				Addressbook.ux.window.Notification.info("Successful", "Item saved");
			},
			failure: function() {
				var grid = me.getView(), plugin = grid.editingPlugin;
				plugin.startEdit(context.record, 0);
			}
		});
	}

});