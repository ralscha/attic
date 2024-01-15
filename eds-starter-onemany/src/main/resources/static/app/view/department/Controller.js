/**
 * Created by Administrator on 25/08/2015.
 */
Ext.define('Starter.view.department.Controller',{
   extend:'Starter.base.ViewController',
    requires: ['Starter.view.department.Form'],

    getObjectStore: function() {
        return this.getStore('department');
    },

    getSelectedObjectViewModelName: function() {
        return 'selectedDepartment';
    },

    onReload:function(){
        this.getStore('department').reload();
    },

    newDepartment:function(){
       this.setSelectedObject(null);
        this.editDepartment(i18n.department_new, new Starter.model.setup.Department());
    },

    editDepartment: function(title, record) {
        var formPanel = this.getView().getLayout().next();
        formPanel.setTitle(title);

        formPanel.loadRecord(record);
        formPanel.isValid();
    },

    onItemclick: function(grid, record) {
        this.editDepartment(i18n.department_edit, this.getSelectedObject());
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


    destroyDepartment: function() {
        var destroyDepartment = this.getSelectedObject();
        if (destroyDepartment) {
            this.destroyObject(destroyDepartment, destroyDepartment.get('departmentName'), function() {
                this.setSelectedObject(null);
            }, function() {
            	Starter.Util.errorToast(i18n.servererror);
                this.setSelectedObject(null);
                this.getObjectStore().reload();
            }, this);
        }
    }
});