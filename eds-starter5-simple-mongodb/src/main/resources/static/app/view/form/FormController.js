Ext.define('SimpleApp.view.form.FormController', {
	extend: 'Ext.app.ViewController',

	fillRemark: function() {
		formLoadService.getRemark(function(result) {
			this.getView().getForm().setValues({
				remarks: result
			});
		}, this);
	},

	load: function() {
		this.getView().getForm().load();
	},

	submit: function() {
		this.getView().getForm().submit({
			success: function(form, action) {
				this.getView().getForm().setValues({
					remarks: action.result.response
				});
			},
			scope: this
		});
	}

});