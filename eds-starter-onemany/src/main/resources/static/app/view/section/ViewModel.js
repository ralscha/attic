/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.section.ViewModel',{
   extend: 'Ext.app.ViewModel',

    requires: ['Starter.model.setup.Section'],

    data:{
        selectedSection: null
    },

    stores:{
        section:{
            model:'Starter.model.setup.Section',
            autoLoad: false,
            remoteSort: true,
            remoteFilter: true,
            pageSize: 25
        }
    },

    formulas: {
        newSection: function(get) {
            var su = get('selectedSection');
            return !su || su.getId() < 0;
        }
    }
});