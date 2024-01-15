Ext.define('Addressbook.controller.Login', {
	extend: 'Deft.mvc.ViewController',
	inject: 'messageBus',

	control: {
		view: {
			enterkey: 'onEnterKey'
		},
		loginButton: {
			click: 'onLoginButton'
		}
	},

	init: function() {
		this.getView().down('#username').focus(false, 20);
	},

	onLoginButton: function() {
		var me = this, email = me.getView().down('#username').getValue().trim(), password = me.getView().down('#password').getValue();

		if (!email.length || !password.length) {
			me.getView().errorMessage('Username and Password are required');
			return;
		}

		me.getLoginButton().disable();
		me.getView().setMessage('Logging in...');

		loginService.login(email, password, function(detail) {
			if (detail.loggedIn) {
				me.messageBus.fireEvent('loginsuccessful');
			} else {
				me.getLoginButton().enable();
				me.getView().errorMessage('Login failed');
			}
		});

	},

	onEnterKey: function(view) {
		this.onLoginButton();
	}

});
