/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.inventory.List', {
    extend: 'Ext.grid.Panel',

    title: i18n.setup_inventory,
    icon:'resources/images/data_scroll.png',

    pageSize: 25,
    width: 420,
    height: 320,
    bind: {
        store: '{inventoryHeaders}',
        selection: '{selectedInventoryHeader}'
    },
    listeners: {
    	itemdblclick: 'onItemclick'
    },

    columns: [ {
        dataIndex: 'userName',
        flex: 1,
        text: 'User Name'
    }, {
        dataIndex: 'enrollNo',
        flex: 1,
        text: 'Enroll No'
    } ,{
        text: 'Department',
        dataIndex: 'departmentId',
        renderer: 'departmentRenderer',
        flex: 1
    },{
        text: 'Section',
        dataIndex: 'sectionId',
        renderer: 'sectionRenderer',
        flex: 1
    },{
        text: 'Location',
        dataIndex: 'locationId',
        renderer: 'locationRenderer',
        flex: 1
    },{
        dataIndex: 'remark',
        flex: 1,
        text: 'Remark'
    },{
        xtype: 'datecolumn',
        format: 'Y-m-d H:i:s',
        text: i18n.lastupdate,
        dataIndex: 'lastUpdate',
        width: 170
    }
    ],

    dockedItems: [ {
        xtype: 'toolbar',
        docked: 'top',
        items: [ {
            text:i18n.create,
            glyph: 0xe807,
            handler: 'newInventory'
        },{
            text:'Reload',
            glyph:0xe81a,
            handler:'onReload'
        },'->', {
            emptyText: i18n.filter,
            xtype: 'textfield',
            triggers: {
                search: {
                    cls: Ext.baseCSSPrefix + 'form-search-trigger',
                    handler: 'onFilter'
                },
                clear: {
                    type: 'clear',
                    hideWhenEmpty: false,
                    handler: 'onFilter'
                }
            },
            listeners: {
                specialKey: 'onFilterSpecialKey'
            }
        } ]
    }, {
        xtype: 'pagingtoolbar',
        dock: 'bottom',
        bind:'{inventoryHeaders}',
        displayInfo: true,
        displayMsg: 'Displaying SopHeader list {0} - {1} of {2}',
        emptyMsg: 'No Inventory list to display.'
    } ]
});
