Ext.define('Ab.view.Login', {
	controller: 'Ab.controller.Login',
	extend: 'Ext.form.Panel',
	alias: 'widget.login',
	requires: [ 'Ab.controller.Login' ],

	frame: true,
	title: 'Login',
	width: 400,
	padding: 5,
	//icon: 'resources/images/login.png',

	defaults: {
		anchor: '100%'
	},

	defaultType: 'textfield',

	fieldDefaults: {
		msgTarget: 'side'
	},

	items: [ {
		fieldLabel: 'Username',
		name: 'username',
		itemId: 'username',
		allowBlank: false
	}, {
		fieldLabel: 'Password',
		name: 'password',
		itemId: 'password',
		inputType: 'password',
		allowBlank: false
	}, {
		xtype: 'displayfield',
		itemId: 'loginMessage',
		fieldLabel: ''
	} ],

	buttons: [ /* <debug> */{
		text: 'Login as admin',
		handler: function() {
			var formPanel = this.up('form'), form = formPanel.getForm();
			form.setValues({
				username: 'admin',
				password: 'admin'
			});
			formPanel.fireEvent('enterkey', this);
		}
	}, {
		text: 'Login as user',
		handler: function() {
			var formPanel = this.up('form'), form = formPanel.getForm();
			form.setValues({
				username: 'user',
				password: 'user'
			});
			formPanel.fireEvent('enterkey', this);
		}
	},/* </debug> */{
		text: 'Login',
		itemId: 'loginButton'
	} ],

	initComponent: function() {
		var me = this, listeners = {
			scope: me,
			specialkey: me.onFieldSpecialKey
		};
		this.callParent();

		Ext.each(this.query('field'), function(tf) {
			tf.on(listeners);
		});
	},

	setMessage: function(text) {
		this.down('#loginMessage').el.update(text);
	},

	errorMessage: function(text) {
		this.down('#loginMessage').el.update('<span style="color: #ea5641">' + text + '</span>');
	},

	onFieldSpecialKey: function(field, e) {
		if (e.getKey() == e.ENTER) {
			this.fireEvent('enterkey', this);
		}
	}
});