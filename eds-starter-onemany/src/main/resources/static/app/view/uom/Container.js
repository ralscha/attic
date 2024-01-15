/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.uom.Container',{
    extend: 'Ext.container.Container',
    requires: [ 'Starter.view.uom.Controller', 'Starter.view.uom.ViewModel', 'Starter.view.uom.List', 'Starter.view.uom.Form' ],

    layout: 'card',

    controller: {
        xclass: 'Starter.view.uom.Controller'
    },

    viewModel: {
        xclass: 'Starter.view.uom.ViewModel'
    },

    items: [ {
        xclass: 'Starter.view.uom.List'
    }, {
        xclass: 'Starter.view.uom.Form'
    } ]
});
