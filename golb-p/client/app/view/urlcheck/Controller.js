Ext.define('Golb.view.urlcheck.Controller', {
	extend: 'Ext.app.ViewController',

	onOpenUrlClick: function(widget) {
		var record = widget.getWidgetRecord();
		window.open(record.get('url'), '_blank');
	},

	onGridRefresh: function() {
		this.getStore('objects').load();
	},
	
	onCheckNowClick: function() {
		urlCheckService.check(function() {
			this.getStore('objects').load();
		}, this);
	}

});