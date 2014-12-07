/**
 * Store for managing car features
 */
Ext.define('CarTracker.store.option.Features', {
	extend: 'CarTracker.store.option.Base',
	alias: 'store.option.feature',
	requires: [ 'CarTracker.model.option.Feature' ],
	storeId: 'Features',
	model: 'CarTracker.model.option.Feature'
});