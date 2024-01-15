Ext.define('Addressbook.controller.ContactInfos', {
	extend: 'Deft.mvc.ViewController',
	requires: [ 'Addressbook.view.ContactInfoEdit' ],
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
		}
	},
	
	init: function() {
		var store = this.getView().getStore();
		store.clearFilter(true);
		store.load({
			params: {
				contactId: this.getView().contactId
			}
		});
	},
	
	onRemoved: function() {
		History.pushState({}, "Addressbook", "?");
	},

	onItemDblClick: function(grid, record) {
		this.editContactInfo(record);
	},

	onCreateButtonClick: function() {
		this.editContactInfo();
	},

	onEditButtonClick: function() {
		this.editContactInfo(this.getView().getSelectionModel().getSelection()[0]);
	},

	editContactInfo: function(record) {
		this.getView().getStore().rejectChanges();

		var editWindow = Ext.create('Addressbook.view.ContactInfoEdit', {
			controller: this
		});

		var form = editWindow.down('form');
		if (record) {
			record.set('contactId', this.getView().contactId);
			form.loadRecord(record);
		} else {
			form.loadRecord(Ext.create('Addressbook.model.ContactInfo', {
				contactId: this.getView().contactId
			}));
		}

		form.isValid();

		editWindow.down('#infoTypeCB').focus();
		editWindow.down('#editFormSaveButton').addListener('click', this.onEditFormSaveButtonClick, this);
	},

	onDestroyButtonClick: function(button) {
		var me = this;
		var store = me.getView().getStore();

		Ext.Msg.confirm('Attention', 'Are you sure you want to delete this info? This action cannot be undone.', function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				var record = me.getView().getSelectionModel().getSelection()[0];
				store.remove(record);
				store.sync({
					success: function() {
						Addressbook.ux.window.Notification.info("Successful", "Info deleted");
					},
					failure: function(records, operation) {
						store.rejectChanges();
						Addressbook.ux.window.Notification.error("Error", "Something wrent wrong");
					}
				});
			}
		});
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
				Addressbook.ux.window.Notification.info("Successful", "Info saved");
				win.close();
			},
			failure: function(records, operation) {
				store.rejectChanges();
			}
		});

	}

});