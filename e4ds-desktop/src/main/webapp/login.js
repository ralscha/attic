Ext.onReady(function() {
	Ext.QuickTips.init();

	var login;
	
	function submitForm() {
		var form = login.getForm();
		if (form.isValid()) {
			form.submit();
		}
	}
	
	login = Ext.create('Ext.form.Panel', {
		frame: true,
		title: 'e4desk',
		url: 'login.html',
		width: 380,
		icon: 'resources/images/key.png',

		standardSubmit: true,

		defaults: {
			anchor: '100%'
		},

		defaultType: 'textfield',

		fieldDefaults: {
			msgTarget: 'side'
		},

		items: [ {
			fieldLabel: i18n.user_email,
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
		} ],

		buttons: [ /* <_debug> */{
			text: i18n.login_withuser,
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'user@e4desk.ch',
					password: 'user'
				});
				form.submit();
			}
		}, {
			text: i18n.login_withadmin,
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'admin@e4desk.ch',
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
		layout: 'fit',
		renderTo: Ext.getBody(),

		items: [ {
			xtype: 'panel',
			border: false,
			style: 'background-color: white',
			layout: {
				type: 'vbox',
				align: 'center',
				pack: 'center'
			},
			items: login,
			dockedItems: [ {
				dock: 'top',
				xtype: 'toolbar',
				items: [ {
					xtype: 'image',
					src: app_context_path + '/resources/images/favicon32.png',
					margin: '2px 10px 2px 5px',
					width: 32,
					height: 32
				}, {
					xtype: 'label',
					text: 'e4desk',
					cls: 'appLabel',
					padding: '5 0 0 0',
					height: 30
				} ]
			} ]
		} ]
	});

	login.getForm().findField('username').focus();

});