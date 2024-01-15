/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.department.Container',{
    extend: 'Ext.container.Container',
    requires: [ 'Starter.view.department.Controller', 'Starter.view.department.ViewModel', 'Starter.view.department.List', 'Starter.view.department.Form' ],

    layout: 'card',

    controller: {
        xclass: 'Starter.view.department.Controller'
    },

    viewModel: {
        xclass: 'Starter.view.department.ViewModel'
    },

    items: [ {
        xclass: 'Starter.view.department.List'
    }, {
        xclass: 'Starter.view.department.Form'
    } ]
});
