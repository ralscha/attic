/**
 * Controller for all car-related management functionality
 */
Ext.define('CarTracker.controller.Reports', {
	extend: 'CarTracker.controller.Base',
	stores: [ 'report.Makes', 'report.Months' ],
	views: [ 'report.make.Dashboard', 'report.make.List', 'report.make.Chart', 'report.month.Dashboard', 'report.month.List', 'report.month.Chart' ],
	refs: [ {
		ref: 'SalesByMakeDashboard',
		selector: '[xtype=report.make.dashboard]'
	}, {
		ref: 'SalesByMonthDashboard',
		selector: '[xtype=report.month.dashboard]'
	} ],
	requires: [ 'Ext.data.JsonStore' ],
	init: function() {
		this.listen({
			controller: {},
			component: {
				'[xtype=report.make.dashboard]': {
					beforerender: this.loadSalesByMake
				},
				'[xtype=report.month.dashboard]': {
					beforerender: this.loadSalesByMonth
				},
				'[xtype=report.make.dashboard] button#refresh': {
					click: this.loadSalesByMakeFullChart
				},
				'[xtype=report.make.list]': {
					itemclick: this.loadSalesByMakeDetailChart
				}
			},
			global: {},
			store: {}
		});
	},
	/**
	 * Loads the component's store
	 * 
	 * @param {Ext.panel.Panel}
	 *            panel
	 * @param {Object}
	 *            eOpts
	 */
	loadSalesByMake: function(panel, eOpts) {
		var me = this, store = panel.down('grid').getStore();
		// load the store
		store.load({
			params: {
				detail: true
			}
		});
		me.loadSalesByMakeFullChart();
	},
	/**
	 * Reloads chart from full grid data
	 */
	loadSalesByMakeFullChart: function() {
		var me = this, view = me.getSalesByMakeDashboard(), chart = view.down('chart'), chartStore = chart.getStore();
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
				chartStore.loadData(tData);
			}
		});

	},
	/**
	 * Filters chart store by selected Make
	 * 
	 * @param {Ext.view.View}
	 *            view
	 * @param {Ext.data.Model}
	 *            record
	 * @param {Object}
	 *            item
	 * @param {Number}
	 *            index
	 * @param {Object}
	 *            e
	 * @param {Object}
	 *            eOpts
	 */
	loadSalesByMakeDetailChart: function(view, record, item, index, e, eOpts) {
		var make = record.get('make'), chart = view.up('[xtype=report.make.dashboard]').down('chart'), 
		gridStore = view.up('[xtype=report.make.dashboard]').down('grid').getStore(),
		chartStore = chart.getStore();
		
		var tData = [];
		var records = gridStore.getRange();
		for ( var j = 0; j < records.length; j++) {
			if (records[j].get('make') === make) {
				tData.push({
					name: records[j].get('model'),
					totalSales: records[j].get('totalSales')
				});
			}
		}
		chartStore.loadData(tData);
	},

	/**
	 * Loads the component's store
	 * 
	 * @param {Ext.panel.Panel}
	 *            panel
	 * @param {Object}
	 *            eOpts
	 */
	loadSalesByMonth: function(panel, eOpts) {
		var me = this, view = me.getSalesByMonthDashboard(), store = view.down('grid').getStore(), chart = view.down('chart');
		chart.store = store;
		store.load();
	},
});