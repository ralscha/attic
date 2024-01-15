/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.location.ViewModel',{
   extend: 'Ext.app.ViewModel',

    requires: ['Starter.model.setup.Location'],

    data:{
        selectedLocation: null
    },

    stores:{
        location:{
            model:'Starter.model.setup.Location',
            autoLoad: false,
            remoteSort: true,
            remoteFilter: true,
            pageSize: 25
        }
    },

    formulas: {
        newLocation: function(get) {
            var su = get('selectedLocation');
            return !su || su.getId() < 0;
        }
    }
});