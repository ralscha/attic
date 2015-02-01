Ext.define('E4ds.controller.Config', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			removed: 'onRemoved'
		},
		saveButton: {
			click: 'onSaveButtonClick'
		},
		testEmailReceiverTf: {
			change: 'onTestEmailReceiverChange'
		},
		sendTestEmailButton: {
			click: 'onSendTestEmailClick'
		}
	},

	init: function() {
		var form = this.getView().getForm();
		appConfigurationService.read(function(result) {
			form.setValues(result);
			form.isValid();
		});
	},

	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
	},

	onTestEmailReceiverChange: function(tf, newValue) {
		if (!Ext.isEmpty(newValue) && tf.isValid()) {
			this.getSendTestEmailButton().setDisabled(false);
		}
		else {
			this.getSendTestEmailButton().setDisabled(true);
		}
	},

	onSaveButtonClick: function() {
		var form = this.getView().getForm();
		appConfigurationService.save(form.getFieldValues(), function() {
			E4ds.ux.window.Notification.info(i18n.successful, i18n.config_saved);
		});
	},

	onSendTestEmailClick: function() {
		var testReceiver = this.getTestEmailReceiverTf().getValue();
		var form = this.getView().getForm();

		if (form.isValid()) {
			appConfigurationService.save(form.getFieldValues(), function() {
				E4ds.ux.window.Notification.info(i18n.successful, i18n.config_saved);
				appConfigurationService.sendTestEmail(testReceiver, function() {
					E4ds.ux.window.Notification.info(i18n.successful,
							i18n.config_testEmailsent);
				});
			});
		}
		else {
			appConfigurationService.sendTestEmail(testReceiver, function() {
				E4ds.ux.window.Notification.info(i18n.successful,
						i18n.config_testEmailsent);
			});
		}
	}

});
