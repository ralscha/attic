Ext.define('Starter.view.main.Main', {
    extend: 'Ext.container.Container',

    controller: {
        xclass: 'Starter.view.main.MainController'
    },
    viewModel: {
        xclass: 'Starter.view.main.MainModel'
    },

    layout: {
        align: 'stretch',
        type: 'vbox'
    },

    items: [{
        xtype: 'container',
        layout: {
            align: 'stretch',
            type: 'hbox'
        },
        flex: 1,
        items: [{
            xclass: 'Starter.view.crud.UserGrid',
            flex: 1,
            margins: 5
        }, {
            xclass: 'Starter.view.poll.PollPanel',
            flex: 1,
            margins: 5
        }]
    }, {
        xtype: 'container',
        layout: {
            align: 'stretch',
            type: 'hbox'
        },
        flex: 1,
        items: [{
            xclass: 'Starter.view.form.FormPanel',
            flex: 1,
            margins: 5
        }, {
            xclass: 'Starter.view.tree.TreePanel',
            flex: 1,
            margins: 5
        }]
    }]

});
