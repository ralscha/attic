/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.location.Controller',{
   extend:'Starter.base.ViewController',
    requires: ['Starter.view.location.Form'],

    getObjectStore: function() {
        return this.getStore('location');
    },

    getSelectedObjectViewModelName: function() {
        return 'selectedLocation';
    },

    onReload:function(){
        this.getStore('location').reload();
    },

    newLocation:function(){
       this.setSelectedObject(null);
        this.editLocation(i18n.location_new, new Starter.model.setup.Location());
    },

    editLocation: function(title, record) {
        var formPanel = this.getView().getLayout().next();
        formPanel.setTitle(title);

        formPanel.loadRecord(record);
        formPanel.isValid();
    },

    onItemclick: function(grid, record) {
        this.editLocation(i18n.location_edit, this.getSelectedObject());
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


    destroyLocation: function() {
        var destroyLocation = this.getSelectedObject();
        if (destroyLocation) {
            this.destroyObject(destroyLocation, destroyLocation.get('locationName'), function() {
                this.setSelectedObject(null);
            }, function() {
               Starter.Util.errorToast(i18n.servererror);
                this.setSelectedObject(null);
                this.getObjectStore().reload();
            }, this);
        }
    }
});