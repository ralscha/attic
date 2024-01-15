/**
 * Created by kmkywar on 27/07/2015.
 */
Ext.define('Starter.view.sop.ViewModel', {
    extend: 'Ext.app.ViewModel',
    requires: ['Starter.model.inventory.SopDetail', 'Starter.model.inventory.SopHeader'],


    data: {
        selectedSopHeader: null
    },

    stores: {
        // Define a store of SopHeader records that links to the Session.
        sopHeaders: {
            model: 'Starter.model.inventory.SopHeader',
            autoLoad: true,
            session: true
        }
    },

    formulas: {
        newSop : function(get) {
            var sop = get('selectedSopHeader');
            return !sop || sop.getId() < 0;
        }
    }
});