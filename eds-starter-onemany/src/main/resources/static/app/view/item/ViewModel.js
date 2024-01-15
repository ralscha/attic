/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.item.ViewModel',{
   extend: 'Ext.app.ViewModel',

    requires: ['Starter.model.setup.Item','Starter.model.setup.Uom'],

    data:{
        selectedItem: null
    },

    stores:{
        items:{
            model:'Starter.model.setup.Item',
            autoLoad: false,
            remoteSort: true,
            remoteFilter: true,
            pageSize: 25
        },
        uoms: {
            model: 'Starter.model.setup.Uom',
            autoLoad: true,
            pageSize:0
        }
    },

    formulas: {
        newItem: function(get) {
            var su = get('selectedItem');
            return !su || su.getId() < 0;
        }
    }
});