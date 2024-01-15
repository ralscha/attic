Ext.onReady(function() {
	Ext.QuickTips.init();

	var header, login;
	
	header = Ext.create('Ext.container.Container', {
		region: 'north',
		height: 35,
		layout: {
			type: 'hbox',
			align: 'stretch'
		},

		items: [ {
			html: 'CT ChangeLog',
			cls: 'appHeader'
		} ]
	});

	function submitForm() {
		var form = login.getForm();
		if (form.isValid()) {
			form.submit();
		}
	}
	
	login = Ext.create('Ext.form.Panel', {
		frame: true,
		title: i18n.login_title,
		url: 'login.html',
		width: 400,
		padding: 5,
		icon: app_context_path + '/resources/images/key.png',

		standardSubmit: true,

		defaults: {
			anchor: '100%'
		},

		defaultType: 'textfield',

		fieldDefaults: {
			msgTarget: 'side'
		},

		items: [ {
			fieldLabel: i18n.user_username,
			name: 'username',
			allowBlank: false,
			listeners: {
				specialkey: function(field, e) {
					if (e.getKey() === e.ENTER) {
						submitForm();
					}
				}
			}
		}, {
			fieldLabel: i18n.user_password,
			name: 'password',
			inputType: 'password',
			allowBlank: false,
			listeners: {
				specialkey: function(field, e) {
					if (e.getKey() === e.ENTER) {
						submitForm();
					}
				}
			}
		}, {
			fieldLabel: i18n.login_rememberme,
			name: 'remember-me',
			xtype: 'checkbox'
		}],

		buttons: [ /* <debug> */{
			text: i18n.login_withuser,
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'user',
					password: 'user'
				});
				form.submit();
			}
		}, {
			text: i18n.login_withadmin,
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'admin',
					password: 'admin'
				});
				form.submit();
			}
		},/* </debug> */{
			text: i18n.login,
			handler: function() {
				submitForm();
			}
		} ]
	});

	Ext.create('Ext.container.Viewport', {
		renderTo: Ext.getBody(),
		
		layout: {
			type: 'border',
			padding: 5
		},

		items: [ header, Ext.create('Ext.Container', {
			layout: {
				type: 'vbox',
				align: 'center',
				pack: 'center'
			},
			region: 'center',
			items: login
		}) ]
	});

	login.getForm().findField('username').focus();

});