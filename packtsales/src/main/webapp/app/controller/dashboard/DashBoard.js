Ext.define('Sales.controller.dashboard.DashBoard', {
	extend: 'Sales.controller.Abstract',
	screenName: 'dashboard',
	init: function() {
		var me = this;
		me.control({
			'myapp-dashboard': {
				'myapp-show': me.onShow,
				'myapp-hide': me.onHide
			}
		});
	},
	onShow: function(p) {
		p.down('myapp-dashboard-pie chart').store.load();
		p.down('myapp-dashboard-bar chart').store.load();
		p.down('myapp-dashboard-line chart').store.load();
		p.down('myapp-dashboard-radar chart').store.load();
	},
	onHide: function() {
	}
});
