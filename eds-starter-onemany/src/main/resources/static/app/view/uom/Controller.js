/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.uom.Controller',{
   extend:'Starter.base.ViewController',
    requires: ['Starter.view.uom.Form'],

    getObjectStore: function() {
        return this.getStore('uom');
    },

    getSelectedObjectViewModelName: function() {
        return 'selectedUom';
    },

    onReload:function(){
        this.getStore('uom').reload();
    },

    newUom:function(){
       this.setSelectedObject(null);
        this.editUom(i18n.uom_new, new Starter.model.setup.Uom());
    },

    editUom: function(title, record) {
        var formPanel = this.getView().getLayout().next();
        formPanel.setTitle(title);

        formPanel.loadRecord(record);
        formPanel.isValid();
    },

    onItemclick: function(grid, record) {
        this.editUom(i18n.uom_edit, this.getSelectedObject());
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


    destroyUom: function() {
        var destroyUom = this.getSelectedObject();
        if (destroyUom) {
            this.destroyObject(destroyUom, destroyUom.get('uomName'), function() {
                this.setSelectedObject(null);
            }, function() {
               Starter.Util.errorToast(i18n.servererror);
                this.setSelectedObject(null);
                this.getObjectStore().reload();
            }, this);
        }
    }
});