/**
 * The main application class. An instance of this class is created by app.js
 * when it calls Ext.application(). This is the ideal place to handle
 * application launch and initialization details.
 */
Ext.define('Todo.Application', {
	extend : 'Ext.app.Application',
	requires : [ 'Todo.*' ],
	name : 'Todo',

	stores : [ 'Type' ],

	defaultToken : 'todo',

	launch : function() {
		// TODO - Launch the application
	},

	onAppUpdate : function() {
		window.location.reload();
	}
});
