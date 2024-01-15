Ext.define('BitP.controller.CrudBase', {
	extend: 'Deft.mvc.ViewController',
	control: {
		view: {
			removed: 'onRemoved',
			itemdblclick: 'onItemDblClick',
			itemcontextmenu: 'onItemContextMenu'
		},
		actionColumn: {
			selector: 'actioncolumn',
			listeners: {
				click: 'onActionColumnClick'
			}
		},
		createButton: {
			click: 'onCreateButtonClick'
		},
		filterField: {
			filter: 'onFilterField'
		}
	},

	constructor: function() {
		if (!Ext.Object.equals(this.control, this.superclass.control)) {
			var toBeDeleted = [];

			Ext.Object.each(this.control, function(key, value, myself) {
				if (!value) {
					toBeDeleted.push(key);
				}
			});

			Ext.merge(this.control, this.superclass.control);

			for (var i = 0; i < toBeDeleted.length; i++) {
				delete this.control[toBeDeleted[i]];
			}
		}

		return this.callParent(arguments);
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

	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
	},

	onItemDblClick: function(grid, record) {
		this.editObject(record);
	},

	onCreateButtonClick: function() {
		this.editObject();
	},

	editObject: function(record) {
		this.getView().getStore().rejectChanges();

		var editWindow = Ext.create(this.editWindowClass);

		var form = editWindow.down('form');
		if (record) {
			form.loadRecord(record);
		} else {
			form.loadRecord(this.createModel());
		}

		form.isValid();

		this.setFocus(editWindow);

		var saveButton = editWindow.down('#editFormSaveButton');
		if (this.isReadonly(record)) {
			Ext.suspendLayouts();
			form.getForm().getFields().each(function(field) {
				field.setReadOnly(true);
			});
			saveButton.setVisible(false);
			Ext.resumeLayouts();
		} else {
			saveButton.addListener('click', this.onEditFormSaveButtonClick, this);
		}

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
						BitP.ux.window.Notification.info(i18n.successful, me.successfulDestroyMsg);
					},
					failure: function(records, operation) {
						store.rejectChanges();
						me.destroyFailureCallback();
					}
				});
			}
		});
	},

	onFilterField: function(field, newValue) {
		var store = this.getView().getStore();
		if (newValue) {
			store.clearFilter(true);
			store.filter('filter', newValue);
		} else {
			store.clearFilter();
		}
	},

	onEditFormSaveButtonClick: function(button) {
		var me = this;
		var win = button.up('window');
		var form = win.down('form');
		var store = this.getView().getStore();

		form.updateRecord();
		var record = form.getRecord();

		if (!record.dirty) {
			win.close();
			return;
		}

		if (record.phantom) {
			store.rejectChanges();
			store.add(record);
		}

		store.sync({
			success: function(records, operation) {
				BitP.ux.window.Notification.info(i18n.successful, me.successfulSaveMsg);
				win.close();
			},
			failure: function(records, operation) {
				store.rejectChanges();
			}
		});
	},

	setFocus: function(editWindow) {
		var query;
		if (this.focusFieldName) {
			query = 'field[name=' + this.focusFieldName + ']';
		} else {
			query = 'field';
		}

		var field = editWindow.down(query);
		if (field) {
			field.focus();
		}
	},

	successfulDestroyMsg: i18n.defaultSuccessfulDestroyMsg,
	successfulSaveMsg: i18n.defaultSuccessfulSaveMsg,

	destroyConfirmMsg: Ext.emptyFn,
	destroyFailureCallback: Ext.emptyFn,

	focusFieldName: null,

	isReadonly: function(record) {
		return false;
	},

	createModel: Ext.emptyFn,

	buildContextMenuItems: function(record) {

		return [ {
			text: i18n.edit,
			hidden: this.isReadonly(record),
			glyph: 0xe803,
			handler: Ext.bind(this.editObject, this, [ record ])
		}, {
			text: i18n.show,
			hidden: !this.isReadonly(record),
			glyph: 0xe817,
			handler: Ext.bind(this.editObject, this, [ record ])
		}, {
			text: i18n.destroy,
			hidden: this.isReadonly(record),
			glyph: 0xe806,
			handler: Ext.bind(this.destroyObject, this, [ record ])
		} ];

	},

	editWindowClass: null
});