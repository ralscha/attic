/*
    This file is generated and updated by Sencha Cmd. You can edit this file as
    needed for your application, but these edits will have to be merged by
    Sencha Cmd when upgrading.
*/
Ext.require([
    'Ext.direct.*',
    'Ext.data.proxy.Direct',
    'Ext.form.action.DirectSubmit'
]);

Ext.onReady(function() {
    Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
});


Ext.application({
    name: 'Sales',

    extend: 'Sales.Application',
    
    autoCreateViewport: true
});
