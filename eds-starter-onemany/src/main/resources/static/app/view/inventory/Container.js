/**
 * Created by Administrator on 25/08/2015.
 */

Ext.define('Starter.view.inventory.Container', {
    extend: 'Ext.panel.Panel',
    requires: ['Starter.view.inventory.Controller','Starter.view.inventory.ViewModel'],


    layout: 'card',
    //session: true,

    viewModel: {
        xclass: 'Starter.view.inventory.ViewModel'
    },

    controller: {
        xclass: 'Starter.view.inventory.Controller'
    },

    items: [{
        xclass: 'Starter.view.inventory.List',
        reference: 'grid'
    },{
        xclass: 'Starter.view.inventory.Form',
        reference: 'form'
    }]
});