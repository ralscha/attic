Ext.define('BitP.controller.Lieferant', {
	extend: 'BitP.controller.CrudBase',
	requires: [ 'BitP.view.lieferant.Edit' ],

	destroyConfirmMsg: function(record) {
		return record.get('firma') + ' ' + i18n.reallyDestroy;
	},

	editWindowClass: 'BitP.view.lieferant.Edit',

	createModel: function() {
		return Ext.create('BitP.model.Lieferant');
	}

});