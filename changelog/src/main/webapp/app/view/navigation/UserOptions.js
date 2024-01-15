Ext.define('Changelog.view.navigation.UserOptions', {
	extend: 'Ext.window.Window',
	stateId: 'useroption',
	title: i18n.options,
    width: 450,
    layout: 'fit',
    resizable: false,
    autoShow: true,
    modal: true,
	icon: app_context_path + '/resources/images/edit.png',
    
	getForm: function() {
		return this.getComponent('editForm').getForm();
	},
    
	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'form',
			itemId: 'editForm',
			padding: 5,
			bodyPadding: 10,
			bodyBorder: true,

			defaultType: 'textfield',
			defaults: {
				anchor: '100%'
			},
					
			api: {
			    submit: userService.userFormPost
			},
			
			baseParams: {
				'options': true,
				'userName': '1'
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
                    fields: ['code', 'language'],
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
				name: 'passwordHash',
				fieldLabel: i18n.user_newpassword,
				inputType: 'password',
				id: 'pass'
			}, {
				name: 'password-confirm',
				fieldLabel: i18n.user_confirmpassword,
				vtype: 'password',
				inputType: 'password',
				initialPassField: 'pass'
            }],

			buttons: [ {
				xtype: 'button',
				text: i18n.save,
				action : 'save',
				icon: app_context_path + '/resources/images/save.png',
				disabled: true,
				formBind: true,
				handler: function() { 
					me.controller.updateUser(me);
				}				
			}, {
				text: i18n.cancel,
				scope: me,
				handler: me.close,
				icon: app_context_path + '/resources/images/cancel.png'
			} ]
		} ];

		me.callParent(arguments);
	}
    
});
