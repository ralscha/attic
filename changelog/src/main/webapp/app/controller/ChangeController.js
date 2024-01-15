Ext.define('Changelog.controller.ChangeController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated',
			itemdblclick: 'editChangeFromDblClick',
			itemclick: 'enableActions'			
		},
		addButton: {
			click: 'createChange'
		},
		editButton: {
			click: 'editChangeFromButton'
		},
		deleteButton: {
			click: 'deleteChange'
		},		
		filterField: {
			filter: 'handleFilter'
		}		
	},	
	

	handleFilter: function(field, newValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('filter', newValue);	
		} else {
			myStore.clearFilter();
		}
	},

	editChangeFromDblClick: function(grid, record) {
		this.editChange(record);
	},

	editChangeFromButton: function() {
		this.editChange(this.getView().getSelectionModel().getSelection()[0]);
	},

	editChange: function(record) {
		var editWindow = Ext.create('Changelog.view.change.Edit');
		editWindow.controller = this;

		var form = editWindow.getForm();
		form.loadRecord(record);		
	},

	createChange: function() {
		var editWindow = Ext.create('Changelog.view.change.Edit');
		editWindow.controller = this;
		
		editWindow.getForm().isValid();
	},

	deleteChange: function(button) {
		var record = this.getView().getSelectionModel().getSelection()[0];
		if (record) {
			Ext.Msg.confirm(i18n.change_delete+'?', i18n.delete_confirm + ' ' + record.data.bugNumber,
					this.afterConfirmDeleteChange, this);
		}
	},

	afterConfirmDeleteChange: function(btn) {
		if (btn === 'yes') {
			var record = this.getView().getSelectionModel().getSelection()[0];
			if (record) {
				this.getView().getStore().remove(record);
				this.doGridRefresh();
				Changelog.ux.window.Notification.info(i18n.successful, i18n.change_deleted);
			}
		}
	},

	enableActions: function(button, record) {
		if (this.getView().getSelectionModel().getSelection()[0]) {
			this.toggleEditButtons(true);
		}
	},

	toggleButton: function(enable, button) {
		if (enable) {
			button.enable();
		} else {
			button.disable();
		}
	},

	toggleEditButtons: function(enable) {
		this.toggleButton(enable, this.getDeleteButton());
		this.toggleButton(enable, this.getEditButton());
	},

	updateChange: function(editWindow) {
		var form = editWindow.getForm(), record = form.getRecord(),
		    values = form.getValues();
		
		if (form.isValid()) {

			if (values.customerIds) {
				var customerIds = [];
				Ext.each(values.customerIds.split(','), function(value) {
					customerIds.push(Number(value));
				});
				values.customerIds = customerIds;
			} else {
				values.customerIds = [];
			}
			
			if (record) {
				record.set(values);				
			} else {
				var newChange = Ext.create('Changelog.model.Change', values);				
				this.getView().getStore().add(newChange);
			}
			this.doGridRefresh();
			
			editWindow.close();
			Changelog.ux.window.Notification.info(i18n.successful, i18n.change_saved);
		}					
	},

	onActivated: function() {
		this.doGridRefresh();			
	},

	doGridRefresh: function() {
		this.getView().getStore().load();
		this.toggleEditButtons(false);
	}

});