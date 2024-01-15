/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.inventory.Controller', {
    extend: 'Starter.base.ViewController',
    requires: [ 'Starter.view.inventory.Form','Starter.model.inventory.InventoryHeader' ],
    
    init: function() {
    	this.getStore('departments').load();
    	this.getStore('sections').load();
    	this.getStore('locations').load();
    	this.getStore('items').load();
    	this.getStore('uoms').load();
    	this.getStore('inventoryHeaders').load();
    },
    
    getObjectStore: function() {
        return this.getStore('inventoryHeaders');
    },

    getSelectedObjectViewModelName: function() {
        return 'selectedInventoryHeader';
    },

    onReload:function(){
        this.getObjectStore().reload();
    },

	back: function() {
		this.getView().getLayout().prev();
	},
	
    onFilterSpecialKey: function(tf, e) {
        if (e.getKey() === e.ENTER) {
            this.onFilter(tf);
        }
    },

    onFilter: function(tf, trigger) {
        if (trigger && trigger.id === 'clear') {
            tf.setValue('');
        }

        var value = tf.getValue();
		var store = this.getStore('inventoryHeaders');
        if (value) {
            this.getViewModel().set('filter', value);
            store.filter('filter', value);
        }
        else {
            this.getViewModel().set('filter', null);
            store.clearFilter();
        }
    },

    onAddItemClick: function() {
    	var detailGrid = this.lookupReference('detailGrid');
        
        var newDetail = Ext.create('Starter.model.inventory.InventoryDetail', {
		    inventoryHeaderId: this.getSelectedObject().getId(),
		    quantity:1
		});
        
        var items = detailGrid.getStore();
        items.add(newDetail);
        detailGrid.plugins[0].startEdit(newDetail, 0);
        
    },

    onRemoveItemClick: function (button) {
        button.getWidgetRecord().drop(false);
    },

    onItemclick: function(grid, record) {
        this.editInventory(i18n.inventory_edit, this.getSelectedObject());
    },

    newInventory: function() {
    	var newih = new Starter.model.inventory.InventoryHeader();
        this.setSelectedObject(newih);
        this.editInventory(i18n.inventory_new, newih);
    },

    editInventory: function(title, record) {
        var formPanel = this.getView().getLayout().next();
        formPanel.setTitle(title);

        formPanel.loadRecord(record);
        formPanel.isValid();
    },
        
	save: function() {
		var form = this.getView().getLayout().getActiveItem().getForm();

		if (form.isValid()) {
			var record = form.getRecord();
		
			if (record.inventoryDetails().getCount() === 0) {
				Starter.Util.errorToast('Please enter at least one Item');
				return;
			}
			
			var data = record.inventoryDetails();
			for (var i = 0; i < data.count(); i++) {
				var r = data.getAt(i);
				if (!r.get('itemId') || !r.get('quantity')) {
					Starter.Util.errorToast('Please enter all required fields in inventory detail');
					return;
				}
			}
			
			
			form.updateRecord(record);			
			this.getView().mask(i18n.saving);
			record.save({
				callback: function(r, op) {
					if (op.success) {
						Starter.Util.successToast(i18n.savesuccessful);
						this.getObjectStore().reload();
						this.setSelectedObject(r);
						this.getView().unmask();
						this.back();
					}
					else {
						Starter.Util.errorToast(i18n.inputcontainserrors);
						if (op.getResponse() && op.getResponse().result && op.getResponse().result.validations) {
							op.getResponse().result.validations.forEach(function(validation) {
								var field = form.findField(validation.field);
								field.markInvalid(validation.messages);
							});
						}
						this.getView().unmask();
					}
				},
				scope: this
			});

		}
	},
    
	destroyInventory: function() {
        var destroySection = this.getSelectedObject();
        if (destroySection) {
            this.destroyObject(destroySection, destroySection.get('userName'), function() {
                this.setSelectedObject(null);
            }, function() {
               Starter.Util.errorToast(i18n.servererror);
                this.setSelectedObject(null);
                this.getObjectStore().reload();
            }, this);
        }
    },
	    
    departmentRenderer: function(value, metaData, record) {
        var departmentStore = this.getStore('departments');
        var department = departmentStore.findRecord('id', value);
        return department !== null ? department.get('departmentName') : value;
    },
    
    sectionRenderer: function(value, metaData, record) {
        var sectionStore = this.getStore('sections');
        var section = sectionStore.findRecord('id', value);
        return section !== null ? section.get('sectionName') : value;
    },
    
    locationRenderer: function(value, metaData, record) {
        var locationStore = this.getStore('locations');
        var location = locationStore.findRecord('id', value);
        return location !== null ? location.get('locationName') : value;
    },
    
    itemCodeRenderer: function(value, metaData, record) {
        var itemsStore = this.getStore('items');
        var item = itemsStore.findRecord('id', value);
        return item !== null ? item.get('code') : value;
    },
    
    itemNameRenderer: function(value, metaData, record) {
        var itemsStore = this.getStore('items');
        var item = itemsStore.findRecord('id', value);
        return item !== null ? item.get('name') : value;
    },
    
    itemUomRenderer: function(value, metaData, record) {
        var itemsStore = this.getStore('items');
        var item = itemsStore.findRecord('id', value);
        if (item) {
        	var uomId = item.get('uomId');
        	var uomStore = this.getStore('uoms');
        	var uom = uomStore.findRecord('id', uomId);
        	if (uom) {
        		return uom.get('uomName');
        	}
        }        
        return value;
    }
    
});