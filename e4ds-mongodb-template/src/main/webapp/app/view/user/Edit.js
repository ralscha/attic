Ext.define('E4ds.view.user.Edit', {
	extend: 'Ext.window.Window',
	alias: 'widget.useredit',
	stateId: 'userEdit',

	title: i18n.user_edit,
	layout: 'fit',
	autoShow: true,
	resizable: true,
	width: 450,
	modal: true,

	iconCls: 'icon-user-edit',

	requires: ['Ext.ux.form.field.BoxSelect'],
	
	initComponent: function() {
		var me = this;
		
		me.items = [ {
			xtype: 'form',
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
			
			fieldDefaults: {
				msgTarget: 'side'
			},

			items: [ {
				name: 'userName',
				fieldLabel: i18n.user_username,
				allowBlank: false
			}, {
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
				name: 'passwordHash',
				fieldLabel: i18n.user_password,				
				inputType: 'password',
				id: 'pass'
			}, {
				name: 'password-confirm',
				fieldLabel: i18n.user_confirmpassword,				
				vtype: 'password',
				inputType: 'password',
				initialPassField: 'pass'
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
				fieldLabel: i18n.user_enabled,
				name: 'enabled',
				xtype: 'checkboxfield',
				inputValue: 'true',
				uncheckedValue: 'false'
			},{
				xtype: 'comboboxselect',
	            name: 'roles',
	            queryMode: 'local',
	            pinList: false,
	            fieldLabel: i18n.user_roles,
                store: Ext.create('Ext.data.ArrayStore', {
                    fields: ['name'],
					data: [ [ 'ROLE_ADMIN' ], [ 'ROLE_USER' ] ]
                }),	            
	            displayField: 'name',
	            valueField: 'name',
	            allowBlank: true
	        } ],

			buttons: [ {
				xtype: 'button',
				text: i18n.save,
				action : 'save',
                iconCls: 'icon-save',
				disabled: true,
				formBind: true
			}, {
				text: i18n.cancel,
				scope: me,
				handler: me.close,
				iconCls: 'icon-cancel'
			} ]
		} ];

		me.callParent(arguments);
	}
});