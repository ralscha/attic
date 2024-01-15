Ext.define('Changelog.view.customer.Builds', {
	extend: 'Ext.window.Window',
	stateId: 'customerBuilds',
	controller: 'Changelog.controller.CustomerBuildController',

	layout: 'fit',
	autoShow: true,
	resizable: true,
	width: 420,
	height: 500,
	modal: true,
	constrainHeader: true,

	icon: app_context_path + '/resources/images/version.png',

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'gridpanel',
			itemId: 'buildGrid',
			store: Ext.create('Changelog.store.CustomerBuilds'),
			columns: [ {
				dataIndex: 'versionNumber',
				flex: 1,
				text: i18n.customer_build,
				editor : {
					allowBlank : false
				}
			}, {
				dataIndex: 'versionDate',
				flex: 1,
				text: i18n.customer_build_date,
				renderer: Ext.util.Format.dateRenderer('d.m.Y'),
				editor: {
	                xtype: 'datefield',
	                allowBlank : false,
	                format: 'd.m.Y',
	                startDay: 1
	            }			
			}, {
				dataIndex: 'internalBuild',
				flex: 1,
				text: i18n.tool_packagebuilder_internalbuild,
				renderer: function(value) {
					if (value === true) {
						return i18n.yes;
					}
					return '';
				},
				editor: {
	                xtype: 'checkboxfield',
	                uncheckedValue: 'false',
					inputValue: 'true'
	            }			
			} ],
			
			plugins : [ Ext.create('Ext.grid.plugin.RowEditing', {
				clicksToMoveEditor : 1
			}) ]
			
		} ];
		
		
		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.customer_build_new,
				disabled: false,
				itemId: 'addButton',
				icon: app_context_path + '/resources/images/version_add.png'
			}, {
				text: i18n.customer_build_edit,
				disabled: true,
				itemId: 'editButton',
				icon: app_context_path + '/resources/images/version_edit.png'
			}, {
				text: i18n.customer_build_delete,
				disabled: true,
				itemId: 'deleteButton',
				icon: app_context_path + '/resources/images/version_delete.png'
			} ]
		} ];

		

		me.callParent(arguments);
	}
});