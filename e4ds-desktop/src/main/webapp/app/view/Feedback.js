Ext.define('E4desk.view.Feedback', {
	extend: 'Ext.window.Window',
	controller: 'E4desk.controller.Feedback',
	stateId: 'E4desk.view.Feedback',
	stateful: true,
	layout: 'border',
	title: i18n.feedback,
	modal: true,
	width: 700,
	height: 600,
	border: false,
	constrain: true,
	icon: app_context_path + '/resources/images/mail_write.png',

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'label',
			region: 'north',
			padding: 5,
			html: 'Send me some feedback....'
		}, {
			xtype: 'htmleditor',
			region: 'center',
			itemId: 'feedbackText',
			padding: 5
		} ];

		me.buttons = [ {
			xtype: 'button',
			itemId: 'sendButton',
			text: i18n.feedback_send,
			icon: app_context_path + '/resources/images/mail_write.png',
			formBind: true
		}, {
			text: i18n.cancel,
			itemId: 'cancelButton',
			icon: app_context_path + '/resources/images/cancel.png'
		} ];

		me.callParent(arguments);
	}
});
