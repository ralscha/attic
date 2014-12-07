/**
 * Store for managing cars
 */
Ext.define('CarTracker.store.Cars', {
	extend: 'CarTracker.store.Base',
	alias: 'store.car',
	requires: [ 'CarTracker.model.Car' ],
	storeId: 'Cars',
	model: 'CarTracker.model.Car'
});