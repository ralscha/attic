Ext.define('Addressbook.controller.AppUsers', {
	extend: 'Deft.mvc.ViewController',
	requires: [ 'Addressbook.view.AppUserEdit' ],
	control: {
		view: {
			removed: 'onRemoved',
			itemdblclick: 'onItemDblClick'
		},
		createButton: {
			click: 'onCreateButtonClick'
		},
		editButton: {
			click: 'onEditButtonClick'
		},
		destroyButton: {
			click: 'onDestroyButtonClick'
		},
		filterField: {
			filter: 'onFilterField'
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

	onItemDblClick: function(grid, record) {
		this.editUser(record);
	},

	onCreateButtonClick: function() {
		this.editUser();
	},

	onEditButtonClick: function() {
		this.editUser(this.getView().getSelectionModel().getSelection()[0]);
	},

	editUser: function(record) {
		this.getView().getStore().rejectChanges();

		var editWindow = Ext.create('Addressbook.view.AppUserEdit');

		var form = editWindow.down('form');
		if (record) {
			form.loadRecord(record);
		} else {
			form.loadRecord(Ext.create('Addressbook.model.AppUser'));
		}

		form.isValid();

		editWindow.down('#userNameTextField').focus();
		editWindow.down('#editFormSaveButton').addListener('click', this.onEditFormSaveButtonClick, this);
	},

	onDestroyButtonClick: function(button) {
		var me = this;
		var store = me.getView().getStore();

		Ext.Msg.confirm('Attention', 'Are you sure you want to delete this user? This action cannot be undone.', function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				var record = me.getView().getSelectionModel().getSelection()[0];
				store.remove(record);
				store.sync({
					success: function() {
						Addressbook.ux.window.Notification.info("Successful", "User deleted");
					},
					failure: function(records, operation) {
						store.rejectChanges();
						Addressbook.ux.window.Notification.error("Error", "Last user cannot be deleted");
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
				Addressbook.ux.window.Notification.info("Successful", "User saved");
				win.close();
			},
			failure: function(records, operation) {
				store.rejectChanges();
			}
		});

	}

});