Ext.define('Starter.view.user.Edit', {
	extend: 'Ext.form.Panel',
	requires: [ 'Starter.Util' ],

	glyph: 0xe803,

	defaultType: 'textfield',
	defaults: {
		anchor: '40%'
	},
	bodyPadding: 10,
	defaultFocus:'textfield[name=loginName]',

	items: [ {
		name:'loginName',
		fieldLabel:i18n.user_loginname,
		allowBlank:false
	}, {
		name: 'firstName',
		fieldLabel: i18n.user_firstname,
		allowBlank: false
	}, {
		name: 'lastName',
		fieldLabel: i18n.user_lastname,
		allowBlank: false
	}, {
		name: 'email',
		fieldLabel: i18n.user_email,
		vtype: 'email',
		allowBlank: false
	},{
		name: 'newPassword',
		fieldLabel: i18n.user_newpassword,
		inputType: 'password',
		validator: function() {
			var newPasswordRetypeField = this.up().getForm().findField('newPasswordRetype');
			var newPasswordRetype = newPasswordRetypeField.getValue();
			var newPassword = this.getValue();
			if ((Ext.isEmpty(newPassword) && Ext.isEmpty(newPasswordRetype)) || (newPassword === newPasswordRetype)) {
				newPasswordRetypeField.clearInvalid();
				return true;
			}
			newPasswordRetypeField.markInvalid(i18n.user_pwdonotmatch);
			return i18n.user_pwdonotmatch;
		}
	}, {
		name: 'newPasswordRetype',
		fieldLabel: i18n.user_newpasswordretype,
		inputType: 'password',
		validator: function() {
			var newPasswordField = this.up().getForm().findField('newPassword');
			var newPassword = newPasswordField.getValue();
			var newPasswordRetype = this.getValue();
			if ((Ext.isEmpty(newPassword) && Ext.isEmpty(newPasswordRetype)) || (newPassword === newPasswordRetype)) {
				newPasswordField.clearInvalid();
				return true;
			}
			newPasswordField.markInvalid(i18n.user_pwdonotmatch);
			return i18n.user_pwdonotmatch;
		}
	},{
		xtype: 'combobox',
		fieldLabel: i18n.user_language,
		name: 'locale',
		store: 'languages',
		valueField: 'value',
		queryMode: 'local',
		emptyText: i18n.user_selectlanguage,
		allowBlank: false,
		forceSelection: true,
		editable: false
	}, {
		fieldLabel: i18n.user_enabled,
		name: 'enabled',
		xtype: 'checkboxfield',
		inputValue: 'true',
		uncheckedValue: 'false'
	}, {
		xtype: 'tagfield',
		fieldLabel: i18n.user_roles,
		bind: {
			store: '{roles}'
		},
		name: 'role',
		displayField: 'name',
		valueField: 'name',
		queryMode: 'local',
		forceSelection: true,
		autoSelect: true,
		delimiter: ',',
		editable: false,
		selectOnFocus: false,
		filterPickList: true
	}, {
		xtype: 'checkbox',
		name: 'passwordReset',
		inputValue: 'true',
		uncheckedValue: 'false',
		labelWidth: 430,
		fieldLabel: i18n.user_passwordreset
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.user_users,
			handler: 'back',
			glyph: 0xe818
		}, '->', {
			text: i18n.user_unlock,
			handler: 'unlock',
			bind: {
				hidden: '{selectedUserNotLocked}'
			}
		}, {
			text: i18n.destroy,
			glyph: 0xe806,
			handler: 'destroyUser',
			bind: {
				hidden: '{newUser}'
			}
		}, {
			text: i18n.user_switchto,
			handler: 'switchTo',
			bind: {
				hidden: '{newUser}'
			}
		}, {
			text: Starter.Util.underline(i18n.save, 'S'),
			accessKey: 's',
			glyph: 0xe80d,
			formBind: true,
			handler: 'save'
		} ]
	} ]

});