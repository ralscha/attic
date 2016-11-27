Ext.define('E4desk.controller.Feedback', {
	extend: 'Deft.mvc.ViewController',

	control: {	
		cancelButton: {
			click: 'onCancelButtonClick'
		},
		sendButton: {
			click: 'onSendButtonClick'
		},
		feedbackText: true
	},
	
	onCancelButtonClick: function() {
		this.getView().close();
	},

	onSendButtonClick: function() {
		var me = this;
		var feedbackText = me.getFeedbackText().getValue();
		infrastructureService.sendFeedback(feedbackText, function() {
			E4desk.ux.window.Notification.info(i18n.successful, i18n.configuration_feedbackEmailsent);
			me.getView().close();	
		});
		
	}

});