Ext.define('Starter.view.crud.UserGrid', {
    extend: 'Ext.grid.Grid',
    requires: ['Ext.grid.plugin.CellEditing'],

    controller: {
        xclass: 'Starter.view.crud.UserController'
    },

    viewModel: {
        xclass: 'Starter.view.crud.UserModel'
    },

    plugins: 'gridcellediting',

    title: 'STORE_READ and STORE_MODIFY',
    bind: {
        store: '{users}',
        selection: '{selectedUser}'
    },

    columns: [{
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
    }],

    items: [{
        xtype: 'toolbar',
        docked: 'top',
        items: [{
            text: 'New',
            handler: 'newUser'
        }, {
            text: 'Delete',
            handler: 'deleteUser',
            bind: {
                disabled: '{!selectedUser}'
            }
        }, '->', {
            placeholder: 'Name and Email Filter',
            xtype: 'textfield',
            listeners: {
                change: {
                    fn: 'onNamefilterChange',
                    buffer: 350
                }
            },
            clearable: true
        }, {
            xtype: 'combobox',
            reference: 'departmentFilterCB',
            placeholder: 'Departments Filter',
            store: 'Departments',
            width: 200,
            queryMode: 'local',
            displayField: 'name',
            valueField: 'id',
            publishes: 'value',
            listeners: {
                change: 'onDepartmentFilterChange'
            },
            clearable: true
        }]
    }]

});
