Ext.define('Golb.view.user.Controller', {
	extend: 'Golb.view.base.ViewController',

	config: {
		formClassName: 'Golb.view.user.Form',
		objectName: i18n.user,
		objectNamePlural: i18n.users,
		reloadAfterEdit: true,
		backAfterSave: true
	},

	erase: function() {
		this.eraseObject(this.getSelectedObject().get('email'), null, function() {
			Golb.Util.errorToast(i18n.user_lastadmin_error);
		}, this);
	},

	switchTo: function() {
		var selectedObject = this.getSelectedObject();
		if (selectedObject) {
			securityService.switchUser(selectedObject.getId(), function(authUser) {
				if (authUser) {
					Golb.app.authUser = authUser;
					var currentLocation = window.location.toString();
					var hashPos = currentLocation.indexOf("#");
					if (hashPos > 0) {
						currentLocation = currentLocation.substring(0, hashPos) + '#auth.signin';
					}
					else {
						currentLocation += '#auth.signin';
					}
					window.location.replace(currentLocation);
					window.location.reload();
				}
			}, this);
		}
	},

	unlock: function() {
		var selectedObject = this.getSelectedObject();
		if (selectedObject) {
			userService.unlock(selectedObject.getId(), function() {
				Golb.Util.successToast(i18n.user_unlocked);
				selectedObject.set('lockedOutUntil', null, {
					commit: true
				});
				this.back();
			}, this);
		}
	},

	disableTwoFactorAuth: function() {
		var selectedObject = this.getSelectedObject();
		if (selectedObject) {
			userService.disableTwoFactorAuth(selectedObject.getId(), function() {
				Golb.Util.successToast(i18n.user_unlocked);
				selectedObject.set('twoFactorAuth', false, {
					commit: true
				});
				this.back();
			}, this);
		}
	},

	sendPwResetReq: function() {
		this.save(function() {
			var selectedObject = this.getSelectedObject();
			if (selectedObject) {
				userService.sendPassordResetEmail(selectedObject.getId(), function() {
					Golb.Util.successToast(i18n.user_sent_pwresetreq);
				});
			}
		});
	}

});