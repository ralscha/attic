Ext.onReady(function() {
	Ext.setGlyphFontFamily('custom');

	var headerContainer, pwResetPanel;

	headerContainer = new Ext.container.Container({
		border: '0 0 2 0',
		style: {
			borderColor: 'black',
			borderStyle: 'solid'
		},
		region: 'north',
		height: 35,
		layout: {
			type: 'hbox'
		},

		items: [ {
			xtype: 'image',
			src: 'resources/images/favicon-32x32.png',
			width: 32,
			height: 32
		}, {
			xtype: 'container',
			html: i18n.app_title,
			cls: 'appHeader'
		} ]
	});

	function submitForm() {
		var form = pwResetPanel.getForm();
		if (form.isValid()) {
			form.submit();
		}
	}

	pwResetPanel = new Ext.form.Panel({
		frame: true,
		title: i18n.login_passwordreset,
		url: 'passwordreset.action',
		width: 400,
		padding: 5,
		glyph: 0xe812,

		standardSubmit: true,

		defaults: {
			anchor: '100%',
			msgTarget: 'qtip'
		},

		defaultType: 'textfield',

		items: [ {
			xtype: 'hiddenfield',
			name: 'token',
			value: passwordResetToken
		}, {
			fieldLabel: i18n.login_passwordreset_newpassword,
			name: 'newPassword',
			allowBlank: false,
			inputType: 'password',
			listeners: {
				specialkey: function(field, e) {
					if (e.getKey() === e.ENTER) {
						submitForm();
					}
				}
			}
		}, {
			fieldLabel: i18n.login_passwordreset_newpasswordretype,
			name: 'newPasswordRetype',
			allowBlank: false,
			inputType: 'password',
			listeners: {
				specialkey: function(field, e) {
					if (e.getKey() === e.ENTER) {
						submitForm();
					}
				}
			},
			validator: function() {
				var newPassword = this.up().getForm().findField('newPassword').getValue();
				var newPasswordRetype = this.getValue();
				if (newPassword === newPasswordRetype) {
					return true;
				}
				return i18n.login_passwordreset_pwdonotmatch;
			}
		} ],

		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'bottom',
			items: [ '->', {
				text: i18n.login_passwordreset_changepassword,
				formBind: true,
				handler: function() {
					submitForm();
				}
			} ]
		} ]

	});

	var centerContainer = new Ext.container.Container({
		xtype: 'container',
		region: 'center',
		layout: 'center',
		items: pwResetPanel
	});

	new Ext.container.Container({
		plugins: 'viewport',
		layout: {
			type: 'border',
			padding: 10
		},
		items: [ headerContainer, centerContainer ]
	});

	pwResetPanel.getForm().findField('newPassword').focus();

	Ext.fly('cssloader').destroy();

});