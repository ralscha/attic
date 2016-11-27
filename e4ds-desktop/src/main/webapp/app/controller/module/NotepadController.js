Ext.define('E4desk.controller.module.NotepadController', {
	extend: 'Deft.mvc.ViewController',
	inject: [ 'messageBus' ],

	config: {
		messageBus: null
	},

	init: function() {
		Ext.logInfo('init E4desk.controller.module.NotepadController');
		this.getMessageBus().fireEvent('refresh', {
			someData: 'from notepad'
		});
	}

});