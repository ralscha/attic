Ext.define('Packt.controller.Login', {
	extend: 'Ext.app.Controller',
	requires: ['Packt.view.Login', 'Packt.view.authentication.CapsLockTooltip', 'Packt.store.security.Groups', 'Packt.store.staticData.Countries' ],
	views: [ 'Login', 'authentication.CapsLockTooltip' ],

	refs: [ {
		ref: 'capslockTooltip',
		selector: 'capslocktooltip'
	} ],

	init: function() {
		this.listen({
			component: {
				"login form button#submit": {
					click: this.onButtonClickSubmit
				},
				"login form button#cancel": {
					click: this.onButtonClickCancel
				},
				'login form button#adminLogin': {
					click: this.onButtonClickAdminLogin
				},
				"login form textfield": {
					specialkey: this.onTextfieldSpecialKey
				},
				"login form textfield[name=password]": {
					keypress: this.onTextfieldKeyPress
				}
			},
			global: {
				aftersuccessfullogin: this.afterSuccessfulLogin
			}
		});
	},

	afterSuccessfulLogin: function() {
		Packt.util.SessionMonitor.start();
		Ext.create('Packt.store.security.Groups', {autoLoad: true});
		Ext.create('Packt.store.staticData.Countries', {autoLoad: true});
	},
	
	onTextfieldKeyPress: function(field, e, options) {
		var charCode = e.getCharCode();
		if ((e.shiftKey && charCode >= 97 && charCode <= 122) || (!e.shiftKey && charCode >= 65 && charCode <= 90)) {
			if (this.getCapslockTooltip() === undefined) {
				Ext.widget('capslocktooltip');
			}
			this.getCapslockTooltip().show();
		} else {
			if (this.getCapslockTooltip() !== undefined) {
				this.getCapslockTooltip().hide();
			}
		}
	},

	onTextfieldSpecialKey: function(field, e, options) {
		var form = field.up('form');
		if (e.getKey() === e.ENTER) {
			if (form.isValid()) {
				var submitBtn = form.down('button#submit');
				submitBtn.fireEvent('click', submitBtn, e, options);
			}
		}
	},

	onButtonClickAdminLogin: function(button, e, options) {
		var formPanel = button.up('form');
		formPanel.down('textfield[name=user]').setValue('admin');
		formPanel.down('textfield[name=password]').setValue('admin');
		this.onButtonClickSubmit(button, e, options);
	},
	
	onButtonClickSubmit: function(button, e, options) {
		var formPanel = button.up('form'), login = button.up('login'), user = formPanel.down('textfield[name=user]').getValue(), pass = formPanel.down(
				'textfield[name=password]').getValue();

		Ext.get(login.getEl()).mask("Authenticating... Please wait...", 'loading');

		loginService.login(user, pass, function(result) {
			Ext.get(login.getEl()).unmask();
			if (result.success) {
				login.close();
				Ext.create('Packt.view.MyViewport');
				Ext.globalEvents.fireEvent('aftersuccessfullogin');
			} else {
				Ext.Msg.show({
					title: 'Fail!',
					msg: result.msg || 'Login failed!',
					icon: Ext.Msg.ERROR,
					buttons: Ext.Msg.OK
				});
			}
		});

	},

	onButtonClickCancel: function(button, e, options) {
		button.up('form').getForm().reset();
	}
});