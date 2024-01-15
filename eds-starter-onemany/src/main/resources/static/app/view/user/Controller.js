Ext.define('Starter.view.user.Controller', {
	extend: 'Starter.base.ViewController',
	requires: [ 'Starter.view.user.Edit' ],

	getObjectStore: function() {
		return this.getStore('users');
	},

	getSelectedObjectViewModelName: function() {
		return 'selectedUser';
	},

	init: function() {
		var store = this.getObjectStore();
		store.load();

		new Ext.LoadMask({
			target: this.getView(),
			msg: i18n.loading,
			store: store
		});
	},

	onFilterSpecialKey: function(tf, e) {
		if (e.getKey() === e.ENTER) {
			this.onFilter(tf);
		}
	},

	onFilter: function(tf, trigger) {
		if (trigger && trigger.id === 'clear') {
			tf.setValue('');
		}

		var value = tf.getValue();
		var store = this.getObjectStore();
		if (value) {
			this.getViewModel().set('filter', value);
			store.filter('filter', value);
		}
		else {
			this.getViewModel().set('filter', null);
			store.clearFilter();
		}
	},

	onItemclick: function(grid, record) {
		this.editUser(i18n.user_edit, this.getSelectedObject());
	},

	newUser: function() {
		this.setSelectedObject(null);
		this.editUser(i18n.user_new, new Starter.model.User());
	},

	editUser: function(title, record) {
		var formPanel = this.getView().getLayout().next();
		formPanel.setTitle(title);

		formPanel.loadRecord(record);
		formPanel.isValid();
	},

	destroyUser: function() {
		var selectedUser = this.getSelectedObject();
		if (selectedUser) {
			this.destroyObject(selectedUser, selectedUser.get('email'), function() {
				this.setSelectedObject(null);
			}, function() {
				Starter.Util.errorToast(i18n.user_lastadmin_error);
				this.setSelectedObject(null);
				this.getObjectStore().reload();
			}, this);
		}
	},

	switchTo: function() {
		var selectedUser = this.getSelectedObject();
		if (selectedUser) {
			securityService.switchUser(selectedUser.getId(), function(ok) {
				if (ok) {
					window.location.reload();
				}
			}, this);
		}
	},

	unlock: function() {
		var selectedUser = this.getSelectedObject();
		if (selectedUser) {
			userService.unlock(selectedUser.getId(), function(success) {
				if (success) {
					Starter.Util.successToast(i18n.savesuccessful);
					selectedUser.set('lockedOutUntil', null, {
						dirty: false
					});
					this.back();
				}
			}, this);
		}
	}

});