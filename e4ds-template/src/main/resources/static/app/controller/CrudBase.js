Ext.define('E4ds.controller.CrudBase', {
	extend: 'Deft.mvc.ViewController',
	control: {
		view: {
			removed: 'onRemoved'
		},
		grid: {
			selector: 'grid',
			listeners: {
				itemclick: 'onItemClick',
				itemdblclick: 'onItemDblClick',
				itemcontextmenu: 'onItemContextMenu'
			}
		},
		actionColumn: {
			selector: 'grid actioncolumn',
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
			var i;

			Ext.Object.each(this.control, function(key, value, myself) {
				if (!value) {
					toBeDeleted.push(key);
				}
			});

			Ext.merge(this.control, this.superclass.control);

			for (i = 0; i < toBeDeleted.length; i++) {
				delete this.control[toBeDeleted[i]];
			}
		}

		return this.callParent(arguments);
	},

	init: function() {
		var store = this.getGrid().getStore();
		store.clearFilter(true);
		store.load();
	},

	destroy: function() {
		if (this.actionMenu) {
			this.actionMenu.destroy();
		}
		return this.callParent(arguments);
	},

	onRemoved: function() {
		if (this.formPanel) {
			this.formPanel.close();
		}

		History.pushState({}, i18n.app_title, "?");
	},

	onItemClick: function(grid, record) {
		if (this.formPanel) {
			this.editObject(record);
		}
	},

	onItemDblClick: function(grid, record) {
		this.editObject(record);
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
		}
		else {
			this.actionMenu.showBy(item);
		}
	},

	onCreateButtonClick: function() {
		this.editObject();
	},

	editObject: function(record) {
		Ext.suspendLayouts();
		this.getGrid().getStore().rejectChanges();

		if (!this.formPanel) {
			this.formPanel = Ext.create(this.formClass, {
				region: 'east'
			});
			this.getView().add(this.formPanel);

			this.formPanel.down('#formSaveButton').addListener('click',
					this.onFormSaveButtonClick, this);
			this.formPanel.down('#formCloseButton').addListener('click',
					this.onFormCloseButtonClick, this);
		}

		var form = this.formPanel.down('form');
		if (record) {
			form.loadRecord(record);
		}
		else {
			form.loadRecord(this.createModel());
		}

		form.isValid();

		if (this.isReadonly(record)) {
			form.getForm().getFields().each(function(field) {
				field.setReadOnly(true);
			});
			this.formPanel.down('#formSaveButton').setVisible(false);
		}

		Ext.resumeLayouts(true);
	},

	destroyObject: function(record) {
		var me = this;
		me.onFormCloseButtonClick();
		var store = me.getGrid().getStore();

		Ext.Msg.confirm(i18n.attention, this.destroyConfirmMsg(record), function(
				buttonId, text, opt) {
			if (buttonId === 'yes') {
				var record = me.getGrid().getSelectionModel().getSelection()[0];
				store.remove(record);
				store.sync({
					success: function() {
						E4ds.ux.window.Notification.info(i18n.successful,
								me.successfulDestroyMsg);
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
		var store = this.getGrid().getStore();
		if (newValue) {
			store.clearFilter(true);
			store.filter('filter', newValue);
		}
		else {
			store.clearFilter();
		}
	},

	onFormSaveButtonClick: function(button) {
		var me = this;
		var form = this.formPanel.down('form');
		var store = this.getGrid().getStore();

		form.updateRecord();
		var record = form.getRecord();

		if (!record.dirty) {
			return;
		}

		if (record.phantom) {
			store.rejectChanges();
			store.add(record);
		}

		store.sync({
			success: function(records, operation) {
				E4ds.ux.window.Notification.info(i18n.successful, me.successfulSaveMsg);
			},
			failure: function(records, operation) {
				store.rejectChanges();
			}
		});
	},

	onFormCloseButtonClick: function() {
		if (this.formPanel) {
			this.formPanel.close();
			this.formPanel = null;
		}
	},

	successfulDestroyMsg: i18n.defaultSuccessfulDestroyMsg,
	successfulSaveMsg: i18n.defaultSuccessfulSaveMsg,

	destroyConfirmMsg: Ext.emptyFn,
	destroyFailureCallback: Ext.emptyFn,

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

	formClass: null
});