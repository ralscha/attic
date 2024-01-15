Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.setGlyphFontFamily('custom');

	var header, login;
	
	header = Ext.create('Ext.container.Container', {
		region: 'north',
		height: 35,
		layout: {
			type: 'hbox',
			align: 'stretch'
		},

		items: [ {
			html: 'bitp-template',
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
		width: 600,
		padding: 5,
		glyph: 0xe812,

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
		} ],

		buttons: [ /* <_debug> */{
			text: 'Bedarf Login',
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'bedarf',
					password: 'bedarf'
				});
				form.submit();
			}
		}, {
			text: 'Einkauf Login',
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'einkauf',
					password: 'einkauf'
				});
				form.submit();
			}
		}, {
			text: 'Lieferant Login',
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					username: 'lieferant',
					password: 'lieferant'
				});
				form.submit();
			}
		}, {
			text: i18n.login_withadmin,
			glyph: 0xe801,
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
			glyph: 0xe801,
			handler: function() {
				submitForm();
			}
		} ]
	});

	Ext.create('Ext.container.Viewport', {
		renderTo: Ext.getBody(),

		style: {
			backgroundColor: '#F8F8F8'
		},		
		
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