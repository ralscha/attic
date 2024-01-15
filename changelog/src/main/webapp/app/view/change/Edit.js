Ext.define('Changelog.view.change.Edit', {
	extend: 'Ext.window.Window',
	stateId: 'changeEdit',

	title: i18n.change_edit,
	layout: 'fit',
	autoShow: true,
	resizable: true,
	width: 800,
	height: 700,
	modal: true,

	icon: app_context_path + '/resources/images/change_edit.png',

	requires: [ 'Ext.ux.form.ItemSelector' ],

	getForm: function() {
		return this.getComponent('changeEditForm').getForm();
	},

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'form',
			itemId: 'changeEditForm',
			padding: 5,
			bodyPadding: 10,
			bodyBorder: true,
			defaultType: 'textfield',
			defaults: {
				anchor: '100%'
			},

			fieldDefaults: {
				msgTarget: 'side',
				labelWidth: 90
			},

			items: [
					{
						xtype: 'container',
						layout: {
							type: 'table',
							columns: 2
						},
						items: [
								{
									xtype: 'combobox',									
									fieldLabel: i18n.change_typ,
									name: 'typ',
									store: Ext.create('Ext.data.ArrayStore', {
										fields: [ 'key', 'value' ],
										data: [ [ 'FIX', i18n.change_typ_fix ], [ 'ENHANCEMENT', i18n.change_typ_enhancement ],
												[ 'NEW', i18n.change_typ_new ] ]
									}),
									valueField: 'key',
									displayField: 'value',
									queryMode: 'local',
									emptyText: i18n.change_selecttype,
									allowBlank: false,
									forceSelection: true,
									padding: '0 30 0 0'
								},
								{
									xtype: 'datefield',
									name: 'implementationDate',
									fieldLabel: i18n.change_implementationdate,
									allowBlank: false,
									value: new Date(),
									format: 'd.m.Y',
									startDay: 1,
									labelWidth: 100
								},
								{
									xtype: 'textfield',
									name: 'bugNumber',
									fieldLabel: i18n.change_bugno,
									allowBlank: true
								},
								{
									xtype: 'combobox',
									fieldLabel: i18n.change_module,
									name: 'module',
									store: Ext.create('Ext.data.ArrayStore', {
										fields: [ 'key', 'value' ],
										data: [ [ 'Partner', 'Partner' ], [ 'Vertrag', 'Vertrag' ], [ 'Alarm', 'Alarm' ],
												[ 'Bestellinfo', 'Bestellinfo' ], [ 'LieBe', 'LieBe' ],
												[ 'Dienste/Customizing', 'Dienste/Customizing' ], [ 'Reporting', 'Reporting' ],
												[ 'Basis', 'Basis' ] ]
									}),
									valueField: 'key',
									displayField: 'value',
									queryMode: 'local',
									emptyText: i18n.change_selectmodule,
									allowBlank: true,
									forceSelection: true,
									labelWidth: 100
								} ]
					},

					{
						name: 'subject',
						fieldLabel: i18n.change_subject,
						allowBlank: false
					}, {
						xtype: 'textareafield',
						name: 'description',
						fieldLabel: i18n.change_description,
						allowBlank: true,
						anchor: '100% -320'
					}, {
						xtype: 'itemselector',
						name: 'customerIds',
						fieldLabel: i18n.customer_customers,
						store: Ext.getStore('customersAllStore'),
						displayField: 'shortName',
						valueField: 'id',
						allowBlank: true,
						height: 240
					} ],

			buttons: [ {
				xtype: 'button',
				text: i18n.save,
				action: 'save',
				icon: app_context_path + '/resources/images/save.png',
				disabled: true,
				formBind: true,
				handler: function() {
					me.controller.updateChange(me);
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