/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.section.Controller',{
   extend:'Starter.base.ViewController',
    requires: ['Starter.view.section.Form'],

    getObjectStore: function() {
        return this.getStore('section');
    },

    getSelectedObjectViewModelName: function() {
        return 'selectedSection';
    },

    onReload:function(){
        this.getStore('section').reload();
    },

    newSection:function(){
       this.setSelectedObject(null);
        this.editSection(i18n.section_new, new Starter.model.setup.Section());
    },

    editSection: function(title, record) {
        var formPanel = this.getView().getLayout().next();
        formPanel.setTitle(title);

        formPanel.loadRecord(record);
        formPanel.isValid();
    },

    onItemclick: function(grid, record) {
        this.editSection(i18n.section_edit, this.getSelectedObject());
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


    destroySection: function() {
        var destroySection = this.getSelectedObject();
        if (destroySection) {
            this.destroyObject(destroySection, destroySection.get('sectionName'), function() {
                this.setSelectedObject(null);
            }, function() {
               Starter.Util.errorToast(i18n.servererror);
                this.setSelectedObject(null);
                this.getObjectStore().reload();
            }, this);
        }
    }
});