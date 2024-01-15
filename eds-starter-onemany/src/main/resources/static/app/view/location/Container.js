/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.location.Container',{
    extend: 'Ext.container.Container',
    requires: [ 'Starter.view.location.Controller', 'Starter.view.location.ViewModel', 'Starter.view.location.List', 'Starter.view.location.Form' ],

    layout: 'card',

    controller: {
        xclass: 'Starter.view.location.Controller'
    },

    viewModel: {
        xclass: 'Starter.view.location.ViewModel'
    },

    items: [ {
        xclass: 'Starter.view.location.List'
    }, {
        xclass: 'Starter.view.location.Form'
    } ]
});
