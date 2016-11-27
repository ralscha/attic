Ext.define('Starter.base.WindowController', {
	extend: 'Ext.app.ViewController',

	getStoreName: function() {
		throw 'Unimplemented method.';
	},

	getSelectedViewModelName: function() {
		throw 'Unimplemented method.';
	},

	close: function() {
		this.getView().close();
	},

	save: function() {
		var form = this.getView().down('form').getForm();

		if (form.isValid()) {
			var record = form.getRecord().copy();
			record.phantom = form.getRecord().phantom;

			form.updateRecord(record);
			this.getView().mask(i18n.saving);
			record.save({
				callback: function(r, op) {
					if (op.success) {
						Starter.Util.successToast(i18n.savesuccessful);
						this.getStore(this.getStoreName()).reload();
						this.getViewModel().set(this.getSelectedViewModelName(), r);
						this.getView().unmask();
						this.close();
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
	}

});