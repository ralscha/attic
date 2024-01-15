/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.uom.ViewModel',{
   extend: 'Ext.app.ViewModel',

    requires: ['Starter.model.setup.Uom'],

    data:{
        selectedUom: null
    },

    stores:{
        uom:{
            model:'Starter.model.setup.Uom',
            autoLoad: false,
            remoteSort: true,
            remoteFilter: true,
            pageSize: 25
        }
    },

    formulas: {
        newUom: function(get) {
            var su = get('selectedUom');
            return !su || su.getId() < 0;
        }
    }
});