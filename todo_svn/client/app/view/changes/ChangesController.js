Ext.define('Todo.view.changes.ChangesController', {
	extend: 'Ext.app.ViewController',
	
	init: function() {
		var me = this;
		Todo.EventBus.start(function() {
			Todo.EventBus.subscribe("historychange", me.onHistoryChange.bind(me));	
		});		
	},
	
	destroy: function() {
		Todo.EventBus.unsubscribe("historychange", this.onHistoryChange);
	},
	
	onHistoryChange: function() {
		this.getStore('changes').reload();
	},
	
	onActivate: function() {
	},
	
	onDeactivate: function() {
	}
	
});