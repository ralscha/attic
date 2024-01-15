Ext.define('Proto.view.usersettings.Controller', {
	extend: 'Ext.app.ViewController',

	init: function() {
		userService.readSettings(function(user) {
			this.getViewModel().set('user', user);
		}, this);
		this.getStore('persistentLogins').load();
	},

	clearState: function() {
		var store = Ext.state.Manager.getProvider().store;
		store.removeAll();
		store.sync({
			callback: function() {
				location.reload();
			}
		});
	},
	
	destroyPersistentLogin: function(grid, rowIndex, colIndex) {		
        var rec = this.getStore('persistentLogins').getAt(rowIndex);
        rec.erase({
        	callback: function() {
        		Proto.Util.successToast(i18n.destroysuccessful);
        	}
        });
    },

	save: function() {
		var vm = this.getViewModel();
		var user = vm.get('user');
		var form = this.getView().down('form').getForm();

		user.newPasswordRetype = form.findField('newPasswordRetype').getValue();
		user.newPassword = form.findField('newPassword').getValue();
		
		this.getView().mask(i18n.saving);
		userService.updateSettings(user, function(validations) {
			if (validations.length > 0) {
				Proto.Util.errorToast(i18n.inputcontainserrors);
				var form = this.lookupReference('userSettingsForm').getForm();
				validations.forEach(function(validation) {					
					var field = form.findField(validation.field);
					field.markInvalid(validation.messages);
				});
				this.getView().unmask();
			}
			else {
				Proto.Util.successToast(i18n.savesuccessful);
				this.getView().unmask();
				this.closeWindow();
			}
		}, this);
	},

	closeWindow: function() {
		this.getView().close();
	}

});