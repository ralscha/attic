/**
 * Created by kmkywar on 27/07/2015.
 */
Ext.define('Starter.view.sop.Controller', {
    extend: 'Ext.app.ViewController',
    requires: [ 'Starter.view.sop.Edit','Starter.model.inventory.SopHeader' ],

    init: function() {
        var store = this.getStore('sopHeaders');
        store.load();

        new Ext.LoadMask({
            target: this.getView(),
            msg: i18n.loading,
            store: store
        });
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
		var store = this.getStore('sopHeaders');
        if (value) {
            this.getViewModel().set('filter', value);
            store.filter('filter', value);
        }
        else {
            this.getViewModel().set('filter', null);
            store.clearFilter();
        }
    },

    onAddOrderClick: function() {
        var orders = this.lookupReference('detailGrid').getStore();
        orders.insert(0, {
            orderdate: new Date(),
            shipped: false
        });
    },

    onRemoveOrderClick: function (button) {
        button.getWidgetRecord().drop();
    },

    onItemclick: function(grid, record) {
		this.editSop('Edit Sop', this.getViewModel().get('selectedSopHeader'));
    },

    newSop: function() {
		this.getViewModel().set('selectedSopHeader', null);
        this.editSop('Add new', new Starter.model.inventory.SopHeader());
    },

    editSop: function(title, record) {
        var formPanel = this.getView().getLayout().next();
		this.getViewModel().set('theSopheader', record);
        formPanel.isValid();
    },

    saveSop: function() {
		var form = this.lookupReference('form');

        if (form.isValid()) {
			var record = this.getViewModel().get('theSopheader');
			var id = record.getId();
			if (id < 0) {
				this.getStore('sopHeaders').add(record);
            }

			this.syncSession();
			this.back();
        }
    },

    destroySop: function() {
		var selectedSop = this.getViewModel().get('selectedSopHeader');
        if (selectedSop) {
			selectedSop.drop();
			this.syncSession();
			this.back();
		}
	},
	
	syncSession: function() {
		var batch = this.getView().getSession().getSaveBatch();
		var changes = this.getSession().getChanges();
		console.log(JSON.stringify(changes));
		if (batch) {
			batch.start();
        }
    }
});