/**
 * Created by kmkywar on 27/07/2015.
 */
Ext.define('Starter.view.sop.Grid', {
    extend: 'Ext.grid.Panel',

    title: i18n.sop_trans,
    icon:'resources/images/data_scroll.png',

    pageSize: 25,
    width: 420,
    height: 320,
    bind: {
        store: '{sopHeaders}',
        selection: '{selectedSopHeader}'
    },
    listeners: {
        itemclick: 'onItemclick'
    },
    viewConfig: {
        loadMask: true
    },


    // Create a session for this view


    columns: [ {
        dataIndex: 'name',
        flex: 1,
        text: 'Name'
    }, {
        dataIndex: 'phone',
        flex: 1,
        text: 'Phone'
    } ],

    dockedItems: [ {
        xtype: 'toolbar',
        docked: 'top',
        items: [ {
            text:i18n.create,
            glyph: 0xe807,
            handler: 'newSop'
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
        bind: {
            store: '{sopHeaders}'
        },
        displayInfo: true,
        displayMsg: 'Displaying SopHeader list {0} - {1} of {2}',
        emptyMsg: 'No SopHeader list to display.'
    } ]
});
