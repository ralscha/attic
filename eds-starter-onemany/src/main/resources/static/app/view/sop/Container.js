/**
 * Created by kmkywar on 27/07/2015.
 */

Ext.define('Starter.view.sop.Container', {
    extend: 'Ext.panel.Panel',
    requires: ['Starter.view.sop.Controller','Starter.view.sop.ViewModel'],


    layout: 'card',
    session: true,

    viewModel: {
        xclass: 'Starter.view.sop.ViewModel'
    },

    controller: {
        xclass: 'Starter.view.sop.Controller'
    },

    items: [{
        xclass: 'Starter.view.sop.Grid',
        reference: 'sopHeaderGrid'
    },{
        xclass: 'Starter.view.sop.Edit',
        reference: 'form'
    }]
});