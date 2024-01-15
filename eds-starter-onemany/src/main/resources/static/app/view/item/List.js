/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.item.List',{
   extend:'Ext.grid.Panel',
    title: i18n.setup_item,
    icon:'resources/images/data_scroll.png',

   bind:{
       store: '{items}',
       selection: '{selectedItem}'
   },

    listeners: {
        itemdblclick: 'onItemclick'
    },
    
    autoLoad: true,

    columns: [
        {
            text: 'Item Code',
            dataIndex: 'code',
            flex:1
        },
        {
            text: 'Item Name',
            dataIndex: 'name',
            flex:1
        },
        {
            text: 'Uom',
            dataIndex: 'uomId',
            renderer: 'uomRenderer',
            flex: 1
        },
        {
            text:'Remark',
            dataIndex: 'remark',
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
                    handler:'newItem'
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
            bind:'{items}',
            displayInfo: true
        }
    ]
});