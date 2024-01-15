Ext.define('TTT.view.Viewport', {
    extend: 'Ext.container.Viewport',
    cls: 'x-border-layout-ct',
    requires: ['TTT.view.MainHeader', 'TTT.view.MainCards', 'Ext.layout.container.VBox'],
    padding: 5,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    items: [{
        xtype: 'mainheader',
        height: 80
    }, {
        xtype: 'maincards',
        flex: 1
    }]
});