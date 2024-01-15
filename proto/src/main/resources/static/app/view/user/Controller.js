Ext.define('Proto.view.user.Controller', {
	extend: 'Proto.base.ViewController',
	requires: [ 'Proto.view.user.Window', 'Proto.store.Roles' ],

	init: function() {
		new Proto.store.Roles();
		
		new Ext.LoadMask({
			target: this.getView(),
			msg: i18n.loading,
			store: this.getStore('users')
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
		var store = this.getStore('users');
		store.clearFilter();
		if (value) {
			this.getViewModel().set('filter', value);

			var emailNameFilter = new Ext.util.Filter({
				filterFn: function(r) {
					var str = '' + r.data.email + ';' + r.data.lastName + ';' + r.data.firstName;
					return (str.toLowerCase().indexOf(value) !== -1);
				}
			});
			store.addFilter(emailNameFilter);
		}
		else {
			this.getViewModel().set('filter', null);
		}
	},

	onItemDoubleClick: function(grid, record) {
		this.editUser(i18n.user_edit, this.getViewModel().get('selectedUser'));
	},

	newUser: function() {
		this.getViewModel().set('selectedUser', null);
		this.editUser(i18n.user_new, new Proto.model.User());
	},

	editUser: function(title, record) {
		var editWin = new Proto.view.user.Window({
			title: title
		});
		this.getView().add(editWin);
		editWin.show();

		var form = editWin.down('form');
		form.loadRecord(record);
		form.isValid();
	},

	destroyUser: function(record) {
		this.destroyEntity(record, record.get('email'), function() {
			this.getViewModel().set('selectedUser', null);
			this.getStore('users').reload();
		}, this);
	},

	switchTo: function(record) {
		if (record) {
			securityService.switchUser(record.getId(), function(ok) {
				if (ok) {
					window.location.reload();
				}
			}, this);
		}
	},

	unlock: function(record) {
		if (record) {
			userService.unlock(record.getId(), function(success) {
				if (success) {
					record.set('lockedOutUntil', null, {
						dirty: false
					});
				}
			});
		}
	},

	buildContextMenuItems: function(record) {
		return [ {
			text: i18n.edit,
			glyph: 0xe803,
			handler: this.editUser.bind(this, i18n.user_edit, record)
		}, {
			text: i18n.user_unlock,
			hidden: !record.data.lockedOutUntil,
			handler: this.unlock.bind(this, record)
		}, {
			text: i18n.destroy,
			glyph: 0xe806,
			handler: this.destroyUser.bind(this, record)
		}, {
			xtype: 'menuseparator'
		}, {
			text: i18n.user_switchto,
			handler: this.switchTo.bind(this, record)
		} ];
	}

});