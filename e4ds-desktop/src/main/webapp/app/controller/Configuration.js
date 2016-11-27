Ext.define('E4desk.controller.Configuration', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			show: 'onShow'
		},
		cancelButton: {
			click: 'onCancelButtonClick'
		},
		saveButton: {
			click: 'onSaveButtonClick'
		},
		configurationEditPanel: true,
		sendTestEmailButton: {
			click: 'onSendTestEmailClick'
		},
		testEmailReceiverTf: {
			change: 'onTestEmailReceiverChange'
		}
	},

	onShow: function() {
		this.getConfigurationEditPanel().load({
			success: function(form) {
				form.isValid();
			}
		});
	},
	
	onTestEmailReceiverChange: function(tf, newValue) {
		if (!Ext.isEmpty(newValue) && tf.isValid()) {
			this.getSendTestEmailButton().setDisabled(false);
		} else {
			this.getSendTestEmailButton().setDisabled(true);
		}
	},

	onCancelButtonClick: function() {
		this.getView().close();
	},

	onSaveButtonClick: function() {
		var me = this;
		this.getConfigurationEditPanel().submit({
			success: function(form, action) {
				E4desk.ux.window.Notification.info(i18n.successful, i18n.configuration_saved);
				me.getView().close();
			}
		});
	},
	
	onSendTestEmailClick: function() {
		var testReceiver = this.getTestEmailReceiverTf().getValue();
		if (this.getConfigurationEditPanel().isValid()) {
			this.getConfigurationEditPanel().submit({
				success: function(form, action) {
					E4desk.ux.window.Notification.info(i18n.successful, i18n.configuration_saved);
					appConfigurationService.sendTestEmail(testReceiver, function() {
						E4desk.ux.window.Notification.info(i18n.successful, i18n.configuration_testEmailsent);
					});
				}
			});
		} else {
			appConfigurationService.sendTestEmail(testReceiver, function() {
				E4desk.ux.window.Notification.info(i18n.successful, i18n.configuration_testEmailsent);
			});
		}
	}

});