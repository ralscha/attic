Ext.define('Changelog.view.customer.List', {
	extend: 'Ext.grid.Panel',
	stateId: 'customerList',
	controller: 'Changelog.controller.CustomerController',
		
	title: i18n.customer_customers,
	closable: true,

	requires: [ 'Changelog.ux.form.field.FilterField' ],

	initComponent: function() {

		var me = this;

		me.store = Ext.create('Changelog.store.Customers');
		
		me.columns = [ {
			text: i18n.customer_shortname,
			dataIndex: 'shortName',
			width: 100
		}, {
			text: i18n.customer_longname,
			dataIndex: 'longName',
			flex: 1
		}, {
			text: i18n.customer_responsible,
			dataIndex: 'responsible',
			width: 120		
		}, {
			text: i18n.customer_installation,
			dataIndex: 'installation',
			width: 100
		}, {
			text: i18n.customer_ftpuser,
			dataIndex: 'ftpUser',
			width: 115
		}, {
			text: i18n.customer_testurl,
			dataIndex: 'testUrl',
			xtype: 'templatecolumn', 
			tpl: '<a href="{testUrl}" target="_blank">{testUrl}</a>',
			flex: 1
		}, {
			text: i18n.customer_latestVersionNumber,
			dataIndex: 'latestVersionNumber',
			width: 120,
			sortable: false
		}, {
			text: i18n.customer_latestVersionDate,
			dataIndex: 'latestVersionDate',
			width: 115,
			renderer: Ext.util.Format.dateRenderer('d.m.Y'),
			sortable: true
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.customer_new,
				disabled: false,
				itemId: 'addButton',
				icon: app_context_path + '/resources/images/customer_add.png'
			}, {
				text: i18n.customer_edit,
				disabled: true,
				itemId: 'editButton',
				icon: app_context_path + '/resources/images/customer_edit.png'
			}, {
				text: i18n.customer_delete,
				disabled: true,
				itemId: 'deleteButton',
				icon: app_context_path + '/resources/images/customer_delete.png'
			}, {
				text: i18n.customer_build_edits,
				disabled: true,
				itemId: 'versionButton',
				icon: app_context_path + '/resources/images/version_edit.png'			
			}, '->', {
				itemId: 'features',
                xtype: 'combobox',
	            fieldLabel: i18n.customer_features,
	            store: Ext.create('Ext.data.ArrayStore', {
	                fields: ['key','feature'],
	                data: [ ['%',i18n.customer_feature_any],
			                ['-',i18n.customer_feature_no],
		                    ['alogin=true',i18n.tool_features_autologin],
			                ['lis=true',i18n.tool_features_lis],
			                ['lms=true',i18n.tool_features_lms],
			                ['ct=true',i18n.tool_features_ct],
			                ['langde=true',i18n.tool_features_langDE],
			                ['langfr=true',i18n.tool_features_langFR],
			                ['langen=true',i18n.tool_features_langEN],
			                ['vertragAutoNr=true',i18n.tool_features_autoNo],
			                ['lic=true',i18n.tool_features_lic],
			                ['clm=true',i18n.tool_features_clm],
			                ['inv=true',i18n.tool_features_inv],
			                ['gen=true',i18n.tool_features_gen],
			                ['par=true',i18n.tool_features_par]
			              ]
	            }),
	            valueField: 'key',
	            displayField: 'feature',
	            queryMode: 'local',
	            width: 400,
				listeners: {
					change: function () {
				        var key = this.getValue();
				        if (key === '%') {
				            this.clearValue();
				        } else {
				        	this.fireEvent('filter', this, key);
				        }
				  	}
				} 
			}, {
				itemId: 'filterField',
				fieldLabel: i18n.filter,
				labelWidth: 40,
				xtype: 'filterfield'
			} ]
		} ];

		me.callParent(arguments);

	}

});