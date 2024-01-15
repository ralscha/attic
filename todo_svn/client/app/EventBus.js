Ext.define("Todo.EventBus", {
	requires: [ 'Ext.data.identifier.Uuid' ],
	singleton: true,

	constructor: function() {
		this.id = Ext.data.identifier.Uuid.create().generate()
	},

	start: function(callback) {
		this.eventSource = new EventSource('eventbus/' + this.id);
		if (callback) {
			this.eventSource.addEventListener('open', callback);
		}
		this.eventSource.addEventListener('error', this.onError, false);
	},

	stop: function() {
		Ext.Ajax.request({
			url: 'eventbus/unsubscribe/' + this.id
		});		
		if (this.eventSource) {
			this.eventSource.close();
			this.eventSource = null;
		}
	},

	onError: function(event) {
		var crashData = {
			type: event.type,
			detail: event.detail
		};
		Ext.Ajax.request({
			url: 'crashlog',
			params: crashData
		});
	},

	subscribe: function(eventName, listener) {
		Ext.Ajax.request({
			url: 'eventbus/subscribe/' + this.id + '/' + eventName
		});
		this.eventSource.addEventListener(eventName, listener, false);
	},

	unsubscribe: function(eventName, listener) {
		Ext.Ajax.request({
			url: 'eventbus/unsubscribe/' + this.id + '/' + eventName
		});		
		this.eventSource.removeEventListener(eventName, listener, false);
	}
});