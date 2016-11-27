Ext.define('Starter.view.config.Controller', {
	extend: 'Ext.app.ViewController',

	init: function() {
		var form = this.getView().getForm();
		appConfigurationService.read(function(result) {
			form.setValues(result);
			form.isValid();
		});
	},

	onSaveButtonClick: function() {
		var form = this.getView().getForm();
		
		this.getView().mask(i18n.saving);
		appConfigurationService.save(form.getFieldValues(), function() {
			Starter.Util.successToast(i18n.config_saved);
			this.getView().unmask();
		}, this);
	},

	onTestEmailReceiverChange: function(tf, newValue) {
		if (!Ext.isEmpty(newValue) && Ext.form.field.VTypes.email(newValue)) {
			this.getViewModel().set('testEmailButtonEnabled', true);
		}
		else {
			this.getViewModel().set('testEmailButtonEnabled', false);
		}
	},

	onSendTestEmailClick: function() {
		var testReceiver = this.getViewModel().get('testEmailReceiver');
		appConfigurationService.sendTestEmail(testReceiver, function() {
			Starter.Util.successToast(i18n.config_testEmailsent);
		});
	}
});
