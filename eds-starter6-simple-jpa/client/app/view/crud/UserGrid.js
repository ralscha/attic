Ext.define('Starter.view.crud.UserGrid', {
	extend: 'Ext.grid.Panel',

	requires: [ 'Ext.grid.plugin.RowEditing' ],

	controller: {
		xclass: 'Starter.view.crud.UserController'
	},
	viewModel: {
		xclass: 'Starter.view.crud.UserModel'
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
		dataIndex: 'departmentId',
		text: 'Department',
		flex: 1,
		renderer: 'departmentRenderer',
		editor: {
			xtype: 'combobox',
			store: 'Departments',
			queryMode: 'local',
			displayField: 'name',
			valueField: 'id'
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
			emptyText: 'Name and Email Filter',
			xtype: 'textfield',
			listeners: {
				change: {
					fn: 'onNamefilterChange',
					buffer: 350
				}
			},
			triggers: {
				clear: {
					cls: 'x-form-clear-trigger',
					weight: 1,
					handler: function(tf) {
						tf.reset();
					}
				}
			}
		}, {
			xtype: 'combobox',
			reference: 'departmentFilterCB',
			emptyText: 'Departments Filter',
			store: 'Departments',
			width: 200,
			queryMode: 'local',
			displayField: 'name',
			valueField: 'id',
			publishes: 'value',
			triggers: {
				clear: {
					cls: 'x-form-clear-trigger',
					weight: 1,
					handler: function(cb) {
						cb.reset();
					}
				}
			}
		} ]
	}, {
		xtype: 'pagingtoolbar',
		reference: 'pagingtoolbar',
		dock: 'bottom',
		displayInfo: true,
		bind: {
			store: '{users}'
		}
	} ]

});