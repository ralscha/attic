Ext.define('E4desk.controller.module.GridWindowController', {
	extend: 'Deft.mvc.ViewController',
	inject: [ 'messageBus' ],

	observe: {
		messageBus: {
			refresh: 'refreshHandler'
		}
	},

	init: function() {
		Ext.logInfo('init');		
	},

	refreshHandler: function(e) {
		Ext.logDebug({dump: e}, "refreshHandler");
	}

});