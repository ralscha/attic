/**
 * Controller for Executive Dashboard functionality
 */
Ext.define('CarTracker.controller.Dashboard', {
	extend: 'CarTracker.controller.Base',
	stores: [ 'report.Makes', 'report.Months' ],
	views: [ 'executive.Dashboard', 'report.make.Chart', 'report.month.Chart', 'car.List' ],
	init: function() {
		this.listen({
			controller: {},
			component: {
				'[xtype=executive.dashboard]': {
					beforerender: this.loadDashboards
				}
			},
			global: {},
			store: {}
		});
	},
	
	/**
	 * Handles initial loading of the executive dashboard
	 * 
	 * @param {Ext.panel.Panel}
	 *            panel
	 * @param {Object}
	 *            eOpts
	 */
	loadDashboards: function(panel, eOpts) {
		var makereport = panel.down('[xtype=report.make.chart]'), monthreport = panel.down('[xtype=report.month.chart]');

		// call report stores manually		
		monthreport.getStore().load();
		
		var makeStore = Ext.create('CarTracker.store.report.Makes');
		makeStore.load({
			params: {
				detail: false
			},
			callback: function(records, operation, success) {
				var tData = [];
				for ( var j = 0; j < records.length; j++) {
					tData.push({
						name: records[j].get('make'),
						totalSales: records[j].get('totalSales')
					});
				}
				if (!makereport.getStore()) {
					makereport.store = Ext.create('Ext.data.JsonStore', {
						fields: [ 'name', 'totalSales' ]
					});
				}
				makereport.getStore().loadData(tData);
			}
		});
		
		
	}
});