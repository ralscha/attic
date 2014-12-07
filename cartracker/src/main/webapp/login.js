Ext.onReady(function() {
	Ext.QuickTips.init();

	var header, login;

	header = Ext.create('Ext.panel.Panel', {
		bodyPadding: 5,
		html: '<img src="resources/images/car.png" /><h1>Car Tracker</h1>',
		cls: 'header',
		region: 'north'
	});

	function submitForm() {
		var form = login.getForm();
		if (form.isValid()) {
			form.submit();
		}
	}

	login = Ext.create('Ext.form.Panel', {
		frame: true,
		title: 'Login',
		url: 'login.jsp#' + unescape(document.location.hash.substring(1)),
		width: 480,
		padding: 5,
		iconCls: 'icon_login',

		standardSubmit: true,

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
			allowBlank: false,
			listeners: {
				specialkey: function(field, e) {
					if (e.getKey() === e.ENTER) {
						submitForm();
					}
				}
			}
		}, {
			fieldLabel: 'Password',
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
			fieldLabel: 'Remember Me',
			name: 'remember-me',
			xtype: 'checkbox'
		} ],

		buttons: [ /* <_debug> */{
			text: 'Admin Login',
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'lillith',
					password: 'password'
				});
				form.submit();
			}
		}, {
			text: 'Auditor Login',
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'kaye',
					password: 'password'
				});
				form.submit();
			}
		}, {
			text: 'Content Manager Login',
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'charles',
					password: 'password'
				});
				form.submit();
			}
		},/* </debug> */{
			text: 'Login',
			handler: function() {
				submitForm();
			}
		} ]
	});

	Ext.create('Ext.container.Viewport', {
		renderTo: Ext.getBody(),
	
		style: {
            backgroundColor: 'white'
        },
	
		layout: {
			type: 'border'
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