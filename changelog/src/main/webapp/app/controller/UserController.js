Ext.define('Changelog.controller.UserController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated',
			itemdblclick: 'editUserFromDblClick',
			itemclick: 'enableActions'			
		},
		addButton: {
			click: 'createUser'
		},
		editButton: {
			click: 'editUserFromButton'
		},
		deleteButton: {
			click: 'deleteUser'
		},
		switchButton: {
			click: 'onSwitchButtonClick'
		},
		filterField: {
			filter: 'handleFilter'
		},	
		exportButton: true
	},
	
	rolesStore: Ext.create('Changelog.store.Roles'),

	handleFilter: function(field, newValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('filter', newValue);
			
			this.getExportButton().setParams({
				filter: newValue
			});			
		} else {
			myStore.clearFilter();
			this.getExportButton().setParams();
		}
	},

	editUserFromDblClick: function(grid, record) {
		this.editUser(record);
	},

	editUserFromButton: function() {
		this.editUser(this.getView().getSelectionModel().getSelection()[0]);
	},

	editUser: function(record) {
		var editWindow = Ext.create('Changelog.view.user.Edit', {
			rolesStore: this.rolesStore,
			controller: this
		});
		
		var form = editWindow.getForm();
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

	createUser: function() {
		var editWindow = Ext.create('Changelog.view.user.Edit', {
			rolesStore: this.rolesStore,
			controller: this
		});
		
		editWindow.getForm().isValid();
	},

	deleteUser: function(button) {
		var record = this.getView().getSelectionModel().getSelection()[0];
		if (record) {
			Ext.Msg.confirm(i18n.user_delete+'?', i18n.delete_confirm + ' ' + record.data.name,
					this.afterConfirmDeleteUser, this);
		}
	},

	onSwitchButtonClick: function() {
		var record = this.getView().getSelectionModel().getSelection()[0];
		if (record) {
			securityService.switchUser(record.data.id, function(ok) {
				if (ok) {
					window.location.reload();
				}
			}, this);
		}
	},

	afterConfirmDeleteUser: function(btn) {
		if (btn === 'yes') {
			var record = this.getView().getSelectionModel().getSelection()[0];
			if (record) {
				this.getView().getStore().remove(record);
				this.getView().getStore().sync();
				this.doGridRefresh();
				Changelog.ux.window.Notification.info(i18n.successful, i18n.user_deleted);
			}
		}
	},

	enableActions: function(button, record) {
		this.toggleEditButtons(true);
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
		this.toggleButton(enable, this.getSwitchButton());
	},
	
	onActivated: function() {
		this.doGridRefresh();
	},

	doGridRefresh: function() {
		this.getView().getStore().load();
		this.toggleEditButtons(false);
	},
	
	updateUser: function(editWindow) {
		var form = editWindow.getForm(), record = form.getRecord();

		form.submit({
			params: {
				id: record ? record.data.id : ''
			},
			scope: this,
			success: function() {
				this.doGridRefresh();
				
				editWindow.close();
				Changelog.ux.window.Notification.info(i18n.successful, i18n.user_saved);
			}
		});
		
	}

});