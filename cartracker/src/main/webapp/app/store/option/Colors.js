/**
 * Store for managing car colors
 */
Ext.define('CarTracker.store.option.Colors', {
	extend: 'CarTracker.store.option.Base',
	alias: 'store.option.color',
	requires: [ 'CarTracker.model.option.Color' ],
	storeId: 'Colors',
	model: 'CarTracker.model.option.Color'
});