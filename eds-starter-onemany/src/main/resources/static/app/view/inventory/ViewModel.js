/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.inventory.ViewModel', {
    extend: 'Ext.app.ViewModel',
    requires: ['Starter.model.inventory.InventoryDetail', 'Starter.model.inventory.InventoryHeader',
                'Starter.model.setup.Department','Starter.model.setup.Section','Starter.model.setup.Location',
                'Starter.model.setup.Item', 'Starter.model.setup.Uom'],


    data: {
        selectedInventoryHeader: null
    },

    stores: {
        inventoryHeaders: {
            model: 'Starter.model.inventory.InventoryHeader',
	    	autoLoad: false,
	        remoteSort: true,
	        remoteFilter: true,
	        pageSize: 25
        },
        departments: {
            model: 'Starter.model.setup.Department',
            autoLoad: false,
            pageSize:0
        },
        sections: {
            model: 'Starter.model.setup.Section',
            autoLoad: false,
            pageSize:0
        },
        locations: {
            model: 'Starter.model.setup.Location',
            autoLoad: false,
            pageSize:0
        },
        items: {
            model: 'Starter.model.setup.Item',
            autoLoad: false,
            pageSize:0
        },
        uoms: {
            model: 'Starter.model.setup.Uom',
            autoLoad: false,
            pageSize:0
        }
    },

    formulas: {
        newInventory : function(get) {
            var sih = get('selectedInventoryHeader');
            return !sih || sih.getId() < 0;
        }
    }
});