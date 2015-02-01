Ext.define('E4ds.view.UserSettings', {
	extend: 'Ext.window.Window',

	title: i18n.settings,
	width: 500,
	layout: 'fit',
	resizable: true,
	constrain: true,
	autoShow: true,
	modal: true,
	glyph: 0xe803,
	defaultFocus: 'field[name=firstName]',

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'form',
			padding: 5,
			bodyPadding: 10,

			defaultType: 'textfield',
			defaults: {
				anchor: '100%'
			},

			fieldDefaults: {
				msgTarget: 'side'
			},

			items: [ {
				name: 'firstName',
				fieldLabel: i18n.user_firstname,
				allowBlank: false
			}, {
				name: 'name',
				fieldLabel: i18n.user_lastname,
				allowBlank: false
			}, {
				name: 'email',
				fieldLabel: i18n.user_email,
				vtype: 'email',
				allowBlank: false
			}, {
				xtype: 'combobox',
				fieldLabel: i18n.user_language,
				name: 'locale',
				store: Ext.create('Ext.data.ArrayStore', {
					fields: [ 'code', 'language' ],
					data: [ [ 'de', i18n.user_language_german ], [ 'en', i18n.user_language_english ] ]
				}),
				valueField: 'code',
				displayField: 'language',
				queryMode: 'local',
				emptyText: i18n.user_selectlanguage,
				allowBlank: false,
				forceSelection: true
			}, {
				xtype: 'label',
				html: '<hr />'
			}, {
				name: 'oldPassword',
				fieldLabel: i18n.user_oldpassword,
				inputType: 'password'
			}, {
				name: 'passwordNew',
				fieldLabel: i18n.user_newpassword,
				inputType: 'password'
			}, {
				name: 'passwordNewConfirm',
				fieldLabel: i18n.user_confirmpassword,
				inputType: 'password'
			} ],

			buttons: [ {
				xtype: 'button',
				itemId: 'formSaveButton',
				text: i18n.save,
				action: 'save',
				glyph: 0xe80d,
				formBind: true
			}, {
				text: i18n.close,
				scope: me,
				handler: me.close,
				glyph: 0xe80e
			} ]
		} ];

		me.callParent(arguments);
	}
});
