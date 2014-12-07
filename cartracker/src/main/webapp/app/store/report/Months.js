/**
 * Store for managing cars
 */
Ext.define('CarTracker.store.report.Months', {
	extend: 'CarTracker.store.Base',
	alias: 'store.report.month',
	requires: [ 'CarTracker.model.report.Month' ],
	storeId: 'report_Months',
	model: 'CarTracker.model.report.Month',
	remoteSort: false,
	remoteGroup: false,
	groupField: 'month',
	sorters: [ {
		property: 'month',
		direction: 'ASC'
	} ]
});