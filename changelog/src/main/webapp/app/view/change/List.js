Ext.define('Changelog.view.change.List', {
	extend: 'Ext.grid.Panel',
	stateId: 'changeList',
	controller: 'Changelog.controller.ChangeController',
	
	title: i18n.change_changes,
	closable: true,

	requires: [ 'Ext.grid.plugin.RowExpander', 'Changelog.ux.form.field.FilterField' ],

	plugins: [ {
		ptype: 'rowexpander',
		expandOnEnter: false,
		expandOnDblClick: false,
		selectRowOnExpand: true,
		rowBodyTpl: [ '<p>{description}</p>', 
		              '<tpl if="customerTooltip">',
		              '<p class="customertooltip">',
		              '<strong>',i18n.customer_customers,': </strong>',
		              '{customerTooltip}',
		              '</p>',
		              '</tpl>' ]
	} ],
	
	initComponent: function() {
		var me = this;
		
		me.store = Ext.create('Changelog.store.Changes');

		me.columns = [ {
			text: i18n.change_implementationdate,
			dataIndex: 'implementationDate',
			width: 135,
			renderer: Ext.util.Format.dateRenderer('d.m.Y')
		}, {
			text: i18n.change_typ,
			dataIndex: 'typ',
			width: 100,
			renderer: function(value) {
				if (value === 'FIX') {
					return i18n.change_typ_fix;
				} 
				
				if (value === 'ENHANCEMENT') {
					return i18n.change_typ_enhancement;
				} 
				
				if (value === 'NEW') {
					return i18n.change_typ_new;
				}
				return '';
			}
		}, {
			text: i18n.change_module,
			dataIndex: 'module',
			width: 130
		}, {
			text: i18n.change_bugno,
			dataIndex: 'bugNumber',
			width: 80
		}, {
			text: i18n.change_subject,
			dataIndex: 'subject',
			flex: 1
		}, {
			text: i18n.change_noofcustomers,
			dataIndex: 'noOfCustomers',
			align: 'right',
			width: 80,
			sortable: false,
			renderer: function(value) {
				if (value > 0) {
					return value;
				}
				return '';
			}
		}];
		
		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.change_new,
				disabled: false,
				itemId: 'addButton',
				icon: app_context_path + '/resources/images/change_add.png'
			}, {
				text: i18n.change_edit,
				disabled: true,
				itemId: 'editButton',
				icon: app_context_path + '/resources/images/change_edit.png'
			}, {
				text: i18n.change_delete,
				disabled: true,
				itemId: 'deleteButton',
				icon: app_context_path + '/resources/images/change_delete.png'
			}, '->', {
				fieldLabel: i18n.filter,
				itemId: 'filterField',
				labelWidth: 40,
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store,
			displayInfo: true,
			displayMsg: i18n.change_display,
			emptyMsg: i18n.change_no
		} ];

		me.callParent(arguments);

	}

});