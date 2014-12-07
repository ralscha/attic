/**
 * Store for managing car images
 */
Ext.define('CarTracker.store.CarImages', {
	extend: 'Ext.data.Store',
	alias: 'store.carimage',
	requires: [ 'CarTracker.model.CarImage' ],
	storeId: 'CarImages',
	model: 'CarTracker.model.CarImage'
});