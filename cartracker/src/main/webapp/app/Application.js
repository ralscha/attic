Ext.util.Format.yesNo = function(v) {
	return v ? 'Yes' : 'No';
};

Ext.define('CarTracker.Application', {
	name: 'CarTracker',
	extend: 'Ext.app.Application',
	requires: [ 'overrides.AbstractMixedCollection', 'Ext.util.History', 'CarTracker.ux.chart.SmartLegend' ],
	views: [ 'Viewport' ],
	controllers: [ 'App', 'Options', 'Staff', 'Cars', 'Security', 'Reports', 'Workflows', 'Dashboard' ],

	launch: function(args) {
		Ext.globalEvents.fireEvent('beforeviewportrender');
	}
});
