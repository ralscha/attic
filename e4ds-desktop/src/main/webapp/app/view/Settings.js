Ext.define('E4desk.view.Settings', {
	extend: 'Ext.window.Window',
	controller: 'E4desk.controller.Settings',
	requires: [ 'E4desk.ux.colorpicker.ColorPicker', 'E4desk.ux.colorpicker.ColorPickerField' ],
	stateId: 'E4desk.view.Settings',
	stateful: true,
	layout: 'fit',
	title: i18n.settings,
	modal: true,
	width: 700,
	height: 600,
	border: false,
	constrain: true,
	icon: app_context_path + '/resources/images/settings-icon.png',

	initComponent: function() {

		this.preview = Ext.create('E4desk.view.Wallpaper', {
			itemId: 'previewWallpaper'
		});

		this.buttons = [ {
			text: i18n.save,
			itemId: 'okButton'
		}, {
			text: i18n.cancel,
			itemId: 'cancelButton'
		} ];

		this.items = Ext.create('Ext.tab.Panel', {

			items: [ Ext.create('Ext.panel.Panel', {
				title: i18n.settings_desktopbackground,
				layout: 'border',
				items: [ {
					xtype: 'panel',
					region: 'west',
					layout: 'fit',
					title: i18n.settings_backgroundimage,
					width: 150,
					items: [ {
						itemId: 'wallpaperDataview',
						xtype: 'dataview',
						store: Ext.create('E4desk.store.WallpaperStore'),
						itemSelector: 'div.settings-wallpaper-item',
						selectedItemCls: 'settings-wallpaper-item-selected',
						overItemCls: 'view-over',
						trackOver: true,
						autoScroll: true,
						tpl: [ '<tpl for=".">', '<div class="settings-wallpaper-item">', '{text}', '</div>', '</tpl>' ]
					} ]
				}, {
					xtype: 'panel',
					title: i18n.settings_preview,
					region: 'center',
					layout: 'fit',
					items: [ this.preview ]
				}, {
					xtype: 'container',
					region: 'south',
					layout: {
						type: 'vbox'
					},
					defaults: {
						margin: 5
					},
					items: [ {
						xtype: 'ux.colorpickerfield',
						itemId: 'backgroundColor',
						fieldLabel: i18n.settings_backgroundcolor
					}, {
						xtype: 'radiogroup',
						itemId: 'picturepos',
						fieldLabel: i18n.settings_pictureposition,
						width: '100%',
						items: [ {
							boxLabel: i18n.settings_center,
							name: 'pos',
							inputValue: 'center'
						}, {
							boxLabel: i18n.settings_tile,
							name: 'pos',
							inputValue: 'tile'
						}, {
							boxLabel: i18n.settings_resize,
							name: 'pos',
							inputValue: 'fit'
						}, {
							boxLabel: i18n.settings_stretch,
							name: 'pos',
							inputValue: 'stretch'
						} ]
					} ]
				} ]
			}), Ext.create('Ext.form.Panel', {
				title: i18n.settings_userinformation,
				itemId: 'userSettingsPanel',
				defaultType: 'textfield',
				defaults: {
					anchor: '100%'
				},
				paramsAsHash: true,
				api: {
					load: userService.userFormSettingsLoad,
					submit: userService.userFormSettingsPost
				},
				fieldDefaults: {
					msgTarget: 'side'
				},
				bodyPadding: 10,
				items: [ {
					name: 'email',
					fieldLabel: i18n.user_email,
					vtype: 'email',
					allowBlank: false,
					maxLength: 255,
					enforceMaxLength: true
				}, {
					name: 'firstName',
					fieldLabel: i18n.user_firstname,
					allowBlank: true,
					maxLength: 255,
					enforceMaxLength: true
				}, {
					name: 'name',
					fieldLabel: i18n.user_name,
					allowBlank: true,
					maxLength: 255,
					enforceMaxLength: true
				}, {
					name: 'passwordHash',
					fieldLabel: i18n.user_password,
					inputType: 'password',
					itemId: 'pass'
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
						fields: [ 'code', 'language' ],
						data: [ [ 'de', i18n.user_language_german ], [ 'en', i18n.user_language_english ] ]
					}),
					valueField: 'code',
					displayField: 'language',
					queryMode: 'local',
					emptyText: i18n.user_selectlanguage,
					allowBlank: false,
					forceSelection: true,
					anchor: '50%'
				} ]
			}), Ext.create('Ext.panel.Panel', {
				title: i18n.settings_reset,
				bodyPadding: 20,
				layout: {
					type: 'vbox'
				},
				items: [ {
					xtype: 'button',
					itemId: 'resetWindowPosButton',
					text: i18n.settings_resetall
				} ]
			}) ]

		});

		this.callParent();
	}
});
