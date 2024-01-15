/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.item.Container',{
    extend: 'Ext.container.Container',
    requires: [ 'Starter.view.item.Controller', 'Starter.view.item.ViewModel', 'Starter.view.item.List', 'Starter.view.item.Form' ],

    layout: 'card',

    controller: {
        xclass: 'Starter.view.item.Controller'
    },

    viewModel: {
        xclass: 'Starter.view.item.ViewModel'
    },

    items: [ {
        xclass: 'Starter.view.item.List'
    }, {
        xclass: 'Starter.view.item.Form'
    } ]
});
