/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.section.Container',{
    extend: 'Ext.container.Container',
    requires: [ 'Starter.view.section.Controller', 'Starter.view.section.ViewModel', 'Starter.view.section.List', 'Starter.view.section.Form' ],

    layout: 'card',

    controller: {
        xclass: 'Starter.view.section.Controller'
    },

    viewModel: {
        xclass: 'Starter.view.section.ViewModel'
    },

    items: [ {
        xclass: 'Starter.view.section.List'
    }, {
        xclass: 'Starter.view.section.Form'
    } ]
});
