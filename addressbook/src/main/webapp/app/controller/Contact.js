Ext.define('Ab.controller.Contact', {
	extend: 'Ab.controller.CrudBase',
	requires: [ 'Ab.view.contact.Edit' ],

	control: {
		contactGroupFilter: {
			change: 'onContactGroupFilter'
		}
	},
	
	onContactGroupFilter: function(field, newValue, oldValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('group', newValue);
		} else {
			myStore.clearFilter();
		}
	},
	
	destroyConfirmMsg: function(record) {
		return record.get('lastName') + ' ' + i18n.reallyDestroy;
	},

	editWindowClass: 'Ab.view.contact.Edit',

	createModel: function() {
		return Ext.create('Ab.model.Contact');
	}

});