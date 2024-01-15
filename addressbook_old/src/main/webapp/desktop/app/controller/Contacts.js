Ext.define('Addressbook.controller.Contacts', {
	extend: 'Deft.mvc.ViewController',
	requires: [ 'Addressbook.view.ContactEdit' ],
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
		},
		contactGroupFilter: {
			change: 'onContactGroupFilter'
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
		var name = record.get('firstName') + ' ' + record.get('lastName');
		
		var state = {
			view: 'Addressbook.view.ContactInfosList',
			viewConfig: {
				icon: 'resources/images/person.png',
				navigationId: 'ci'+record.getId(),
				contactId: record.getId(),
				title: name
			}
		};
		
		History.pushState(state, "Addressbook: " + name, "?cid=" + record.getId());
	},

	onCreateButtonClick: function() {
		this.editContact();
	},

	onEditButtonClick: function() {
		this.editContact(this.getView().getSelectionModel().getSelection()[0]);
	},

	editContact: function(record) {
		this.getView().getStore().rejectChanges();

		var editWindow = Ext.create('Addressbook.view.ContactEdit', {
			controller: this
		});

		var form = editWindow.down('form');
		if (record) {
			form.loadRecord(record);
		} else {
			form.loadRecord(Ext.create('Addressbook.model.Contact'));
		}

		form.isValid();

		editWindow.down('#lastNameTextField').focus();
		editWindow.down('#editFormSaveButton').addListener('click', this.onEditFormSaveButtonClick, this);
	},

	onDestroyButtonClick: function(button) {
		var me = this;
		var store = me.getView().getStore();

		Ext.Msg.confirm('Attention', 'Are you sure you want to delete this contact? This action cannot be undone.', function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				var record = me.getView().getSelectionModel().getSelection()[0];
				store.remove(record);
				store.sync({
					success: function() {
						Addressbook.ux.window.Notification.info("Successful", "Contact deleted");
					},
					failure: function(records, operation) {
						store.rejectChanges();
						Addressbook.ux.window.Notification.error("Error", "Something wrent wrong");
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

	onContactGroupFilter: function(field, newValue, oldValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('group', newValue);
		} else {
			myStore.clearFilter();
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
				Addressbook.ux.window.Notification.info("Successful", "Contact saved");
				win.close();
			},
			failure: function(records, operation) {
				store.rejectChanges();
			}
		});

	}

});