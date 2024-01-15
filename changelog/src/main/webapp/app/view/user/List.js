Ext.define('Changelog.view.user.List', {
	extend: 'Ext.grid.Panel',

	controller: 'Changelog.controller.UserController',

	stateId: 'userList',
		
	title: i18n.user_users,
	closable: true,

	requires: [ 'Changelog.ux.form.field.FilterField', 'Changelog.model.Role' ],

	initComponent: function() {

		var me = this;

		me.store = Ext.create('Changelog.store.Users');
		
		me.columns = [ {
			text: i18n.user_username,
			dataIndex: 'userName',
			flex: 1
		}, {
			text: i18n.user_firstname,
			dataIndex: 'firstName',
			flex: 1
		}, {
			text: i18n.user_lastname,
			dataIndex: 'name',
			flex: 1
		}, {
			text: i18n.user_email,
			dataIndex: 'email',
			flex: 1
		}, {
			text: 'Roles',
			dataIndex: 'roles',
			sortable: false,
			width: 160,
			renderer: function(value, metadata, record) {
				var roles = record.roles();
				var result = '';
				if (roles) {
					roles.each(function(item, index, count) {
						result += item.get('name');
						if (index + 1 < count) {
							result += ', ';
						}
					});
				}
				metadata.tdAttr = 'data-qtip="' + result + '"';
				return result;
			}
		}, {
			text: i18n.user_enabled,
			dataIndex: 'enabled',
			width: 70,
			renderer: function(value) {
				if (value === true) {
					return i18n.yes;
				}
				return i18n.no;
			}
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.user_new,
				disabled: false,
				itemId: 'addButton',
				icon: app_context_path + '/resources/images/user_add.png'
			}, {
				text: i18n.user_edit,
				disabled: true,
				itemId: 'editButton',
				icon: app_context_path + '/resources/images/user_edit.png'
			}, {
				text: i18n.user_delete,
				disabled: true,
				itemId: 'deleteButton',
				icon: app_context_path + '/resources/images/user_delete.png'
			}, '-', {
				text: i18n.excelexport,
				itemId: 'exportButton',
				icon: app_context_path + '/resources/images/excel.gif',
				href: 'usersExport.xlsx',
				hrefTarget: '_self'
			}, {
				xtype: 'tbseparator'
			}, {
				text: i18n.user_switchto,
				itemId: 'switchButton',
				icon: app_context_path + '/resources/images/spy.png',
				disabled: true
			}, '->', {
				itemId: 'filterField',
				fieldLabel: i18n.filter,
				labelWidth: 40,
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store,
			displayInfo: true,
			displayMsg: i18n.user_display,
			emptyMsg: i18n.user_nodata
		} ];

		me.callParent(arguments);

	}

});