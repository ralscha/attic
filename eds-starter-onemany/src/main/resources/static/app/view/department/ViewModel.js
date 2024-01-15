/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.department.ViewModel',{
   extend: 'Ext.app.ViewModel',

    requires: ['Starter.model.setup.Department'],

    data:{
        selectedDepartment: null
    },

    stores:{
        department:{
            model:'Starter.model.setup.Department',
            autoLoad: false,
            remoteSort: true,
            remoteFilter: true,
            pageSize: 25
        }
    },

    formulas: {
        newDepartment: function(get) {
            var su = get('selectedDepartment');
            return !su || su.getId() < 0;
        }
    }
});