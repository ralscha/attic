Ext.onReady(function() {
	Ext.setGlyphFontFamily('custom');
	Ext.tip.QuickTipManager.init();

	var header, login;
	var capslockwarningText = '<div style="font-weight: bold;">' + i18n.login_capslockwarning_title + '</div><br />' + '<div>'
			+ i18n.login_capslockwarning_line1 + '</div><br />' + '<div>' + i18n.login_capslockwarning_line2 + '</div>';

	header = Ext.create('Ext.container.Container', {
		region: 'north',
		height: 35,
		layout: {
			type: 'hbox',
			align: 'stretch'
		},

		items: [ {
			html: i18n.app_title,
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
			validateOnBlur: false,
			allowBlank: false,
			enableKeyEvents: true,
			listeners: {
				specialkey: function(field, e) {
					if (e.getKey() === e.ENTER) {
						submitForm();
					}
				},
				render: {
					fn: function(field, eOpts) {
						field.capsWarningTooltip = Ext.create('Ext.tip.ToolTip', {
							target: field.bodyEl,
							anchor: 'top',
							width: 305,
							html: capslockwarningText
						});
						field.capsWarningTooltip.disable();
					},
					scope: this
				},
				keypress: {
					fn: function(field, e, eOpts) {
						var charCode = e.getCharCode();
						if ((e.shiftKey && charCode >= 97 && charCode <= 122) || (!e.shiftKey && charCode >= 65 && charCode <= 90)) {
							field.capsWarningTooltip.enable();
							field.capsWarningTooltip.show();
						} else {
							if (field.capsWarningTooltip.hidden === false) {
								field.capsWarningTooltip.disable();
								field.capsWarningTooltip.hide();
							}
						}
					},
					scope: this
				},
				blur: function(field) {
					if (field.capsWarningTooltip.hidden === false) {
						field.capsWarningTooltip.hide();
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
			glyph: 0xe801,
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