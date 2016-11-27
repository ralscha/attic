Ext.define('E4desk.controller.User', {
	extend: 'Deft.mvc.ViewController',
	requires : [ 'E4desk.store.Users' ],
	store: Ext.create('E4desk.store.Users'),

	observe: {
		store: {
			load: 'onStoreLoad'
		}
	},

	control: {
		view: {
			show: 'onShow'
		},
		grid: {
			itemdblclick: 'onGridItemDblClick',
			itemclick: 'onGridItemClick',
			selectionchange: 'onSelectionChange'
		},
		addButton: {
			click: 'onAddButtonClick'
		},
		editButton: {
			click: 'onEditButtonClick'
		},
		deleteButton: {
			click: 'onDeleteButtonClick'
		},
		switchButton: {
			click: 'onSwitchButtonClick'
		},
		filterField: {
			filter: 'handleFilter'
		},
		exportButton: true,
		form: {
			show: 'onFormShow'
		},
		cancelButton: {
			click: 'onCancelButtonClick'
		},
		saveButton: {
			click: 'onSaveButtonClick'
		}
	},

	handleFilter: function(field, newValue) {
		var myStore = this.getGrid().getStore();
		if (newValue) {
			myStore.remoteFilter = false;
			myStore.clearFilter(true);
			myStore.remoteFilter = true;
			myStore.filter('filter', newValue);

			this.getExportButton().setParams({
				filter: newValue
			});
		} else {
			myStore.clearFilter();
			this.getExportButton().setParams();
		}
	},

	onGridItemDblClick: function(grid, record) {
		this.edit(record);
	},

	onSelectionChange: function() {
		if (this.getGrid().getSelectionModel().hasSelection()) {
			this.getDeleteButton().enable();
			this.getEditButton().enable();
			this.getSwitchButton().enable();
		} else {
			this.getDeleteButton().disable();
			this.getEditButton().disable();
			this.getSwitchButton().disable();			
		}
	},

	onFormShow: function(panel) {
		panel.getComponent('email').focus();
	},

	onGridItemClick: function(grid, record) {
		if (this.getForm().isVisible()) {
			this.edit(record);
		}
	},

	onEditButtonClick: function() {
		this.edit(this.getGrid().getSelectionModel().getSelection()[0]);
	},

	onCancelButtonClick: function() {
		this.getForm().setVisible(false);
	},

	edit: function(record) {
		var editPanel = this.getForm();
		editPanel.setVisible(true);

		var form = editPanel.getForm();
		form.loadRecord(record);
		var roles = [];
		
		if (record.roles()) {
			roles = Ext.Array.map(record.roles().getRange(), function(item) {
				return item.get('id');
			});	
		} 

		form.setValues({
			'roleIds': roles
		});	

	},

	onAddButtonClick: function() {
		this.getGrid().getSelectionModel().deselectAll();
		var editPanel = this.getForm();
		editPanel.setVisible(true);
		editPanel.getForm().reset(true);
		editPanel.getForm().isValid();
	},

	onDeleteButtonClick: function(button) {
		var record = this.getGrid().getSelectionModel().getSelection()[0];
		if (record) {
			Ext.Msg.confirm(i18n.user_deleteuserquestion, 
				i18n.user_deleteuserconfirm + ' ' + record.data.name, 
				this.afterConfirmDelete, this);
		}
	},

	onSwitchButtonClick: function() {
		var record = this.getGrid().getSelectionModel().getSelection()[0];
		if (record) {
			infrastructureService.switchUser(record.data.id, function(ok) {
				if (ok) {
					window.location.reload();
				}
			}, this);
		}
	},
	
	afterConfirmDelete: function(btn) {
		if (btn === 'yes') {
			var record = this.getGrid().getSelectionModel().getSelection()[0];
			if (record) {
				this.getGrid().getStore().remove(record);
				record.destroy();
				this.getGrid().getStore().load();
				E4desk.ux.window.Notification.info(i18n.successful, i18n.user_deleted);
			}
		}
	},

	onShow: function() {
		this.getGrid().getStore().load();
	},

	onStoreLoad: function() {
		if (this.getForm().isVisible()) {
			this.getForm().setVisible(false);
		}
	},

	onSaveButtonClick: function() {
		var form = this.getForm().getForm(), record = form.getRecord();

		form.submit({
			params: {
				id: record ? record.data.id : ''
			},
			scope: this,
			success: function() {
				this.getGrid().getStore().load();
				E4desk.ux.window.Notification.info(i18n.successful, i18n.user_saved);
			}
		});

	}

});