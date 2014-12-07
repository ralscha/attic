Ext.define('Packt.controller.security.Users', {
	extend: 'Ext.app.Controller',

	requires: [ 'Packt.store.security.Groups', 'Packt.view.security.Users', 'Packt.view.security.Profile' ],

	views: [ 'security.Users', 'security.Profile' ],

	stores: [ 'security.Groups' ],

	refs: [ {
		ref: 'usersList',
		selector: 'userslist'
	}, {
		ref: 'userPicture',
		selector: 'profile image'
	} ],

	init: function(application) {

		this.control({
			"userslist": {
				render: this.onRender
			},
			"users button#add": {
				click: this.onButtonClickAdd
			},
			"users button#edit": {
				click: this.onButtonClickEdit
			},
			"users button#delete": {
				click: this.onButtonClickDelete
			},
			"profile button#save": {
				click: this.onButtonClickSave
			},
			"profile button#cancel": {
				click: this.onButtonClickCancel
			},
			"profile filefield": {
				change: this.onFilefieldChange
			}
		});

	},

	onRender: function(component, options) {
		if (!component.up('groupsedit')) {
			component.getStore().load();
		}
	},

	onButtonClickAdd: function(button, e, options) {
		var win = Ext.create('Packt.view.security.Profile');
		win.down('form').loadRecord(Ext.create('Packt.model.security.User', {appGroupId:null}));
		win.setTitle('Add new User');
		win.show();
	},

	onButtonClickEdit: function(button, e, options) {

		var grid = this.getUsersList(), record = grid.getSelectionModel().getSelection();

		if (record[0]) {

			var editWindow = Ext.create('Packt.view.security.Profile');

			editWindow.down('form').loadRecord(record[0]);

			if (record[0].get('picture')) {

				var img = editWindow.down('image');
				img.setSrc(record[0].get('picture'));
			}

			editWindow.setTitle(record[0].get('name'));
			editWindow.show();
		}
	},

	onButtonClickDelete: function(button, e, options) {
		var grid = this.getUsersList(), record = grid.getSelectionModel().getSelection(), store = grid.getStore();

		if (store.getCount() >= 2 && record[0]) {

			Ext.Msg.show({
				title: 'Delete?',
				msg: 'Are you sure you want to delete?',
				buttons: Ext.Msg.YESNO,
				icon: Ext.Msg.QUESTION,
				fn: function(buttonId) {
					if (buttonId === 'yes') {
						
						store.remove(record[0]);
						store.sync({
							success: function() {
								Packt.util.Alert.msg('Success!', 'User deleted.');
							},
							failure: function(records, operation) {
								store.rejectChanges();
								Packt.util.Util.showErrorMsg('User not deleted');
							}
						});						
					}
				}
			});
		} else if (store.getCount() === 1) {
			Ext.Msg.show({
				title: 'Warning',
				msg: 'You cannot delete all the users from the application.',
				buttons: Ext.Msg.OK,
				icon: Ext.Msg.WARNING
			});
		}
	},

	onButtonClickSave: function(button, e, options) {

		var win = button.up('window'), formPanel = win.down('form'), form = formPanel.getForm(), store = this.getUsersList().getStore();

		if (form.isValid()) {
		
			form.updateRecord();
			var record = form.getRecord();

			if (!record.dirty) {
				return;
			}

			if (record.phantom) {
				store.rejectChanges();
				store.add(record);
			}

			store.sync({
				success: function(records, operation) {
					Packt.util.Alert.msg('Success!', 'User saved.');
					win.close();
				},
				failure: function(records, operation) {
					Packt.util.Util.showErrorMsg('User not saved');
					store.rejectChanges();
				}
			});			
		}
	},

	onButtonClickCancel: function(button, e, options) {
		button.up('window').close();
	},

	onFilefieldChange: function(filefield, value, options) {
		var pictureField = filefield.up('form').down('hiddenfield[name=picture]');
		
		var file = filefield.fileInputEl.dom.files[0];

		var picture = this.getUserPicture();

		/*
		 * If the file is an image and the web browser supports FileReader, present a preview in the image component
		 */
		if (typeof FileReader !== "undefined" && (/image/i).test(file.type)) {
			var reader = new FileReader();
			reader.onload = function(e) {		
				picture.setSrc(e.target.result);
				pictureField.setValue(e.target.result);
			};
			reader.readAsDataURL(file);
		} else if (!(/image/i).test(file.type)) {
			Ext.Msg.alert('Warning', 'You can only upload image files!');
			filefield.reset();
		}
	}
});