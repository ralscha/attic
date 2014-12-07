/**
 * Store for managing cars
 */
Ext.define('CarTracker.store.report.Makes', {
	extend: 'CarTracker.store.Base',
	alias: 'store.report.make',
	requires: [ 'CarTracker.model.report.Make' ],
	model: 'CarTracker.model.report.Make',
	remoteSort: false,
	remoteGroup: false,
	groupField: 'make'
});