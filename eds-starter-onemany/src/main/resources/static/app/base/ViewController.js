Ext.define('Starter.base.ViewController', {
	extend: 'Ext.app.ViewController',

	back: function() {
		this.getView().getLayout().prev();
	},

	getObjectStore: function() {
		throw 'Unimplemented method.';
	},

	getSelectedObjectViewModelName: function() {
		throw 'Unimplemented method.';
	},

	getSelectedObject: function() {
		return this.getViewModel().get(this.getSelectedObjectViewModelName());
	},

	setSelectedObject: function(so) {
		this.getViewModel().set(this.getSelectedObjectViewModelName(), so);
	},

	save: function() {
		var form = this.getView().getLayout().getActiveItem().getForm();

		if (form.isValid()) {
			var record = form.getRecord().copy();
			record.phantom = form.getRecord().phantom;

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

	destroyObject: function(record, errormsg, successCallback, errorCallback, scope) {
		Ext.Msg.confirm(i18n.attention, Ext.String.format(i18n.destroyConfirmMsg, errormsg), function(choice) {
			if (choice === 'yes') {
				record.erase({
					callback: function(records, operation, success) {
						if (success) {
							Starter.Util.successToast(i18n.destroysuccessful);
							if (successCallback) {
								successCallback.call(scope);
							}
							this.back();
						}
						else {
							if (errorCallback) {
								errorCallback.call(scope);
							}
							else {
								Starter.Util.errorToast(i18n.servererror);
							}
							this.back();
						}
					},
					scope: this
				});
			}
		}, this);
	}

});