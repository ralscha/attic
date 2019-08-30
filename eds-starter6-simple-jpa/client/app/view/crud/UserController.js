Ext.define('Starter.view.crud.UserController', {
	extend: 'Ext.app.ViewController',

	onNamefilterChange(field, newValue) {
		this.getViewModel().set('nameFilter', newValue);
	},

	deleteUser() {
		Ext.Msg.confirm('Really delete?', 'Are you sure?', this.onDeleteUserConfirm, this);
	},

	departmentRenderer(value) {
		if (value) {
			return Ext.getStore('Departments').getById(value).get('name');
		}
		return value;
	},

	onDeleteUserConfirm(choice) {
		if (choice === 'yes') {
			const selectedUser = this.getViewModel().get('selectedUser');
			selectedUser.erase({
				callback: e => {
					Ext.toast({
						html: 'User deleted',
						title: 'Info',
						width: 200,
						align: 't',
						shadow: true
					});
					this.getStore('users').load();
				}
			});
		}
	},

	onCancelEdit(editor, context, eOpts) {
		if (context.record.phantom) {
			this.getStore('users').remove(context.record);
		}
	},

	onEdit() {
		this.getStore('users').sync();
	},

	newUser() {
		const newUser = new Starter.model.User({
			lastName: 'New',
			firstName: 'Person',
			email: 'new@email.com',
			department: 'Customer Service'
		});

		this.getStore('users').insert(0, newUser);
		this.getView().getPlugin('storePanelRowEditing').startEdit(0, 0);
	}

});