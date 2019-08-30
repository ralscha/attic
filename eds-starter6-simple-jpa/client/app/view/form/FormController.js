Ext.define('Starter.view.form.FormController', {
	extend: 'Ext.app.ViewController',

	fillRemark() {
		formLoadService.getRemark(result => {
			this.getView().getForm().setValues({
				remarks: result
			});
		});
	},

	load() {
		this.getView().getForm().load();
	},

	submit() {
		this.getView().getForm().submit({
			success: (form, action) => {
				this.getView().getForm().setValues({
					remarks: action.result.response
				});
			}
		});
	}

});