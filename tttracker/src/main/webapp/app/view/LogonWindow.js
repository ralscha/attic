Ext.define('TTT.view.LogonWindow', {
    extend: 'Ext.window.Window',
    xtype: 'logonwindow',
    closable: false,
    iconCls: 'logo-small',
    width: 350,
    bodyPadding: 10,
    title: 'Task Time Tracker Logon',
    requires: ['Ext.form.field.Text'],
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [{
                xtype: 'textfield',
                fieldLabel: 'User Name',
                name: 'username',
                value: '',
                allowBlank: false,
                validateOnBlur: true,
                emptyText: 'Enter a Username'
            }, {
                xtype: 'textfield',
                name: 'password',
                value: '',
                fieldLabel: 'Password',
                inputType: 'password',
                validateOnBlur: true,
                allowBlank: false
            }, {
                xtype: 'toolbar',
                ui: 'footer',
                layout: {
                    pack: 'end',
                    type: 'hbox'
                },
                items: [{
                    xtype: 'button',
                    text: 'Logon'
                }]
            },
            {
                xtype:'container',   
                style:{
                    textAlign:'center'
                },
                html:' Version ' + TTT.BUILD_VERSION + ' built on ' + TTT.BUILD_DATE
            }
            ]
        });
        me.callParent(arguments);
    }
});