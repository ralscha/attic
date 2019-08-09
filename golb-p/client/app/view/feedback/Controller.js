Ext.define('Golb.view.feedback.Controller', {
	extend: 'Golb.view.base.ViewController',

	config: {
		objectName: 'Feedback',
		objectNamePlural: 'Feedbacks',
		reloadAfterEdit: false,
		backAfterSave: true
	},

	onDeleteClick: function(widget) {
		var record = widget.getWidgetRecord();

		var store = this.getStore(this.getObjectStoreName());
		store.totalCount = store.getTotalCount() - 1;

		record.erase({
			success: function(record, operation) {
			},
			failure: function(record, operation) {
				store.totalCount = store.getTotalCount() + 1;
				store.add(record);
				Golb.Util.errorToast(i18n.servererror);
			},
			scope: this
		});
	}

});