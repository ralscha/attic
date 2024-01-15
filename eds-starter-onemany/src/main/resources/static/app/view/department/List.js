/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.department.List',{
   extend:'Ext.grid.Panel',
    title: i18n.setup_department,
    icon:'resources/images/data_scroll.png',

   bind:{
       store: '{department}',
       selection: '{selectedDepartment}'
   },

    listeners: {
        itemdblclick: 'onItemclick'
    },

    autoLoad: true,

    columns: [
        {
            text: 'Department Name',
            dataIndex: 'departmentName',
            flex:1
        },
        {
            text:'Note',
            dataIndex: 'notes',
            flex:1
        },
        {
            xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
            text: i18n.lastupdate,
            dataIndex: 'lastUpdate',
            width: 170
        }
    ],
    dockedItems: [
        {
            xtype: 'toolbar',
            dock:'top',
            items:[
                {
                    text:i18n.create,
                    glyph: 0xe807,
                    handler:'newDepartment'
                },
                {
                    text:'Reload',
                    glyph:0xe81a,
                    handler:'onReload'
                },'->',{
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

                }

            ]
        },
        {
            xtype: 'pagingtoolbar',
            dock:'bottom',
            bind:'{department}',
            displayInfo: true
        }
    ]
});