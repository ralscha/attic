/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.item.Controller',{
   extend:'Starter.base.ViewController',
    requires: ['Starter.view.item.Form'],

    getObjectStore: function() {
        return this.getStore('items');
    },

    getSelectedObjectViewModelName: function() {
        return 'selectedItem';
    },

	uomRenderer: function(value, metaData, record) {
        var uomStore = this.getStore('uoms');
        var uom = uomStore.findRecord('id', value);
        return uom !== null ? uom.get('uomName') : value;
    },
    
    onReload:function(){
        this.getStore('items').reload();
    },

    newItem:function(){
       this.setSelectedObject(null);
        this.editItem(i18n.item_new, new Starter.model.setup.Item());
    },

    editItem: function(title, record) {
        var formPanel = this.getView().getLayout().next();
        formPanel.setTitle(title);

        formPanel.loadRecord(record);
        formPanel.isValid();
    },

    onItemclick: function(grid, record) {
        this.editItem(i18n.item_edit, this.getSelectedObject());
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
        var store = this.getObjectStore();
        if (value) {
            this.getViewModel().set('filter', value);
            store.filter('filter', value);
        }
        else {
            this.getViewModel().set('filter', null);
            store.clearFilter();
        }
    },


    destroyItem: function() {
        var destroyItem = this.getSelectedObject();
        if (destroyItem) {
            this.destroyObject(destroyItem, destroyItem.get('name'), function() {
                this.setSelectedObject(null);
            }, function() {
                Starter.Util.errorToast(i18n.servererror);
                this.setSelectedObject(null);
                this.getObjectStore().reload();
            }, this);
        }
    }
});