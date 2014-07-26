Ext.define('E4ds.controller.Users', {
	extend: 'Ext.app.Controller',

	views: [ 'user.List', 'user.Edit' ],
	stores: [ 'Users' ],
	models: [ 'User' ],
	refs: [ {
		ref: 'userList',
		selector: 'userlist'
	}, {
		ref: 'userEditForm',
		selector: 'useredit form'
	}, {
		ref: 'userEditWindow',
		selector: 'useredit'
	}, {
		ref: 'exportButton',
		selector: 'userlist button[action=export]'
	}, {
		ref: 'pagingtoolbar',
		selector: 'userlist pagingtoolbar'
	} ],

	init: function() {
		this.control({
			'userlist': {
				itemdblclick: this.editUserFromDblClick,
				itemclick: this.enableActions,
				add: this.onAdd								
			},
			'useredit button[action=save]': {
				click: this.updateUser
			},
			'userlist button[action=add]': {
				click: this.createUser
			},
			'userlist button[action=edit]': {
				click: this.editUserFromButton
			},
			'userlist button[action=delete]': {
				click: this.deleteUser
			},
			'userlist textfield': {
				filter: this.handleFilter,
			}
		});
	},

	handleFilter: function(field, newValue) {
		var myStore = this.getUsersStore();
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

	editUserFromDblClick: function(grid, record) {
		this.editUser(record);
	},

	editUserFromButton: function() {
		this.editUser(this.getUserList().getSelectionModel().getSelection()[0]);
	},

	editUser: function(record) {
		Ext.widget('useredit');

		var form = this.getUserEditForm().getForm();
		form.loadRecord(record);
	},

	createUser: function() {
		Ext.widget('useredit');
		this.getUserEditForm().getForm().isValid();
	},

	deleteUser: function(button) {
		var record = this.getUserList().getSelectionModel().getSelection()[0];
		if (record) {
			Ext.Msg.confirm(i18n.user_delete+'?', i18n.delete_confirm + ' ' + record.data.name,
					this.afterConfirmDeleteUser, this);
		}
	},

	afterConfirmDeleteUser: function(btn) {
		if (btn === 'yes') {
			var record = this.getUserList().getSelectionModel().getSelection()[0];
			if (record) {
				this.getUsersStore().remove(record);
				this.getUsersStore().sync();
				this.doGridRefresh();
				Ext.ux.window.Notification.info(i18n.successful, i18n.user_deleted);
			}
		}
	},

	enableActions: function(button, record) {
		this.toggleEditButtons(true);
	},

	toggleButton: function(enable, buttonId) {
		var button = this.getUserList().down(buttonId);
		if (enable) {
			button.enable();
		} else {
			button.disable();
		}
	},

	toggleEditButtons: function(enable) {
		this.toggleButton(enable, 'button[action=delete]');
		this.toggleButton(enable, 'button[action=edit]');
	},

	updateUser: function(button) {
		var form = this.getUserEditForm(), record = form.getRecord();

		form.getForm().submit({
			params: {
				id: record ? record.data.id : ''
			},
			scope: this,
			success: function(form, action) {
				this.doGridRefresh();
				this.getUserEditWindow().close();
				Ext.ux.window.Notification.info(i18n.successful, i18n.user_saved);
			}
		});
	},

	onAdd: function() {
		this.getUsersStore().clearFilter();
	},

	doGridRefresh: function() {
		this.getPagingtoolbar().doRefresh();
		this.toggleEditButtons(false);
	}

});