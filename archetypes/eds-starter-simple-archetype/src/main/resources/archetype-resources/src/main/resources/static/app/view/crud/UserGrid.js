Ext.define('${jsAppNamespace}.view.crud.UserGrid', {
	extend: 'Ext.grid.Panel',

	requires: [ '${jsAppNamespace}.view.crud.UserController', '${jsAppNamespace}.view.crud.UserModel' ],

	controller: {
		xclass: '${jsAppNamespace}.view.crud.UserController'
	},
	viewModel: {
		xclass: '${jsAppNamespace}.view.crud.UserModel'
	},

	title: 'STORE_READ and STORE_MODIFY',
	bind: {
		store: '{users}',
		selection: '{selectedUser}'
	},

	listeners: {
		canceledit: 'onCancelEdit',
		edit: 'onEdit'
	},

	columns: [ {
		dataIndex: 'firstName',
		text: 'First Name',
		flex: 1,
		editor: {
			xtype: 'textfield',
			allowBlank: false
		}
	}, {
		dataIndex: 'lastName',
		text: 'Last Name',
		flex: 1,
		editor: {
			xtype: 'textfield',
			allowBlank: false
		}
	}, {
		dataIndex: 'email',
		text: 'Email',
		flex: 1,
		editor: {
			xtype: 'textfield',
			allowBlank: false,
			vtype: 'email'
		}
	}, {
		dataIndex: 'department',
		text: 'Department',
		flex: 1,
		editor: {
			xtype: 'combobox',
			store: 'Departments',
			queryMode: 'local',
			displayField: 'name',
			valueField: 'name'
		}
	} ],

	plugins: {
		ptype: 'rowediting',
		pluginId: 'storePanelRowEditing'
	},

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: 'New',
			handler: 'newUser'
		}, {
			text: 'Delete',
			handler: 'deleteUser',
			bind: {
				disabled: '{!selectedUser}'
			}
		}, '->', {
			fieldLabel: 'Filter',
			labelWidth: 40,
			xtype: 'textfield',
			listeners: {
				change: {
					fn: 'filterChange',
					buffer: 350
				}
			}
		} ]
	}, {
		xtype: 'pagingtoolbar',
		reference: 'pagingtoolbar',
		dock: 'bottom',
		displayInfo: true
	} ]

});