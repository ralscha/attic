Ext.define('TTT.view.MainHeader', {
    extend: 'Ext.container.Container',
    xtype: 'mainheader',
    requires: ['Ext.toolbar.Toolbar'],
    layout: {
        align: 'stretch',
        type: 'hbox'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [{
                xtype: 'container',
                cls: 'logo',
                width: 300
            }, {
                xtype: 'toolbar',
                flex: 1,
                ui: 'footer',
                layout: {
                    pack: 'end',
                    padding: '20 20 0 0',
                    type: 'hbox'
                },
                items: [{
                    xtype: 'button',
                    itemId: 'taskLogsBtn',
                    iconCls: 'tasklog',
                    text: 'Task Logs'
                }, {
                    xtype: 'button',
                    itemId: 'taskAdminBtn',
                    iconCls: 'admin',
                    hidden: !TTT.getApplication().isAdmin(),
                    text: '3T Admin'
                }, {
                    xtype: 'button',
                    itemId: 'userAdminBtn',
                    hidden: !TTT.getApplication().isAdmin(),
                    iconCls: 'users',
                    text: 'Users'
                }, '->',
                {
                    xtype: 'button',
                    itemId: 'logoffBtn',
                    iconCls: 'logoff',
                    text: 'Logoff'
                }]
            }]
        });
        me.callParent(arguments);
    }
});